package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.impl.Validation;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public User addUser(User user) {
        if (doesUserExistById(user.getId()) || user.getId() != null) {
            log.debug("{} already exist", user);
            throw new UserAlreadyExistException("User already exist");
        }
        Validation.validateUser(user);
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "EMAIL", user.getId(),
                "LOGIN", user.getLogin(),
                "USERNAME", user.getUsername(),
                "BIRTHDAY", user.getBirthday()
        ));
        user.setId(id.intValue());
        log.info("User added: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!doesUserExistById(user.getId())) {
            log.debug("{} ID User doesn't exist", user.getId());
            throw new UserNotExistException("ID User doesn't exist");
        }
        Validation.validateUser(user);
        jdbcTemplate.update(
                "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USERNAME = ?, BIRTHDAY = ? WHERE ID = ?",
                user.getEmail(),
                user.getLogin(),
                user.getUsername(),
                user.getBirthday(),
                user.getId()
        );
        log.info("User updated: {}", user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return jdbcTemplate
                .query("SELECT * FROM users ORDER BY ID", new UserRowMapper());
    }

    @Override
    public User getUser(Integer id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE ID = ?",
                    new UserRowMapper(),
                    id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserNotExistException("User with id: " + id + " not found", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error accessing the database for user with id: " + id, e);
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) {
            try {
                return User.builder()
                        .email(rs.getString("EMAIL"))
                        .login(rs.getString("LOGIN"))
                        .username(rs.getString("USERNAME"))
                        .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                        .build();
            } catch (SQLException e) {
                throw new RuntimeException("Error mapping result set to User object", e);
            }
        }
    }

    private boolean doesUserExistById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERS WHERE ID = ?", Integer.class, id)).orElse(0) > 0;
    }
}
