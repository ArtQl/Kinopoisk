package ru.artq.practice.kinopoisk.storage.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.UserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.UserStorage;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.FilmRowMapper;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.UserRowMapper;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class UserDTO implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public UserDTO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public User addUser(User user) {
        if (doesUserExistById(user.getId())) {
            log.debug("{} already exist", user);
            throw new UserAlreadyExistException("User already exist");
        }
        if (user.getId() != null) {
            log.debug("{} ID user already exist", user.getId());
            throw new UserIdException("ID user already exist");
        }
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "EMAIL", user.getEmail(),
                "LOGIN", user.getLogin(),
                "USERNAME", user.getUsername(),
                "BIRTHDAY", Date.valueOf(user.getBirthday())
        ));
        user.setId(id.intValue());
        log.info("User added: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!doesUserExistById(user.getId())) {
            log.debug("{} ID User doesn't exist", user.getId());
            throw new UserIdException("ID User doesn't exist");
        }
        String sql = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USERNAME = ?, BIRTHDAY = ? WHERE ID = ?";
        jdbcTemplate.update(sql,
                user.getEmail(), user.getLogin(),
                user.getUsername(), user.getBirthday(),
                user.getId()
        );
        log.info("User updated: {}", user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        System.out.println("in db");
        String sql = "SELECT * FROM users ORDER BY ID";
        Collection<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        if (users.isEmpty()) {
            log.debug("Users no added");
            throw new UserNotExistException("Users no added");
        }
        return users;
    }

    @Override
    public User getUser(Integer id) {
        try {
            String sql = "SELECT * FROM USERS WHERE ID = ?";
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserNotExistException("User with id: " + id + " not found", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error accessing the database for user with id: " + id, e);
        }
    }

    @Override
    public void clear() {
        String sql = "SELECT COUNT(*) as res FROM USERS";
        Integer res = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("res"));
        if (res != null && res > 0)
            jdbcTemplate.execute("DELETE FROM USERS WHERE ID > 0; ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1");
    }

    @Override
    public Collection<Film> recommendations(Integer id) {
        String sql = """
                SELECT DISTINCT f.*
                FROM LIKES l1
                JOIN LIKES l2 ON l1.FILM_ID = l2.FILM_ID
                JOIN FILMS f ON l2.FILM_ID = f.ID
                WHERE l1.USER_ID = ?
                  AND l2.USER_ID != ?
                  AND l2.FILM_ID NOT IN (
                    SELECT FILM_ID FROM LIKES WHERE USER_ID = ?
                  );
                """;
        return jdbcTemplate.query(sql, new FilmRowMapper(), id, id, id);
    }

    private Boolean doesUserExistById(Integer id) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE ID = ?";
        return Optional.ofNullable(jdbcTemplate
                        .queryForObject(sql, Integer.class, id))
                .orElse(0) > 0;
    }
}