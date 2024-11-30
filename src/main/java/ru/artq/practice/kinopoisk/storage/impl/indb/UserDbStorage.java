package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        try {
            return User.builder()
                    .id(rs.getInt("ID"))
                    .email(rs.getString("EMAIL"))
                    .login(rs.getString("LOGIN"))
                    .username(rs.getString("USERNAME"))
                    .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to User object", e);
        }
    };

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
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
            throw new InvalidUserIdException("ID user already exist");
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
            throw new InvalidUserIdException("ID User doesn't exist");
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
        String sql = "SELECT * FROM users ORDER BY ID";
        Collection<User> users = jdbcTemplate.query(sql, rowMapper);
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
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserNotExistException("User with id: " + id + " not found", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error accessing the database for user with id: " + id, e);
        }
    }

    @Override
    public void clearUsers() {
        jdbcTemplate.execute("DELETE FROM USERS");
    }

    private Boolean doesUserExistById(Integer id) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE ID = ?";
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(sql, Integer.class, id))
                .orElse(0) > 0;
    }
}