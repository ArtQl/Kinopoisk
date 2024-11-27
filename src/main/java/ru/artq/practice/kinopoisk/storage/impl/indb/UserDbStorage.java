package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.inter.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        if (getUsers().contains(user) || user.getId() != null) {
            log.debug("{} already exist", user);
            throw new UserAlreadyExistException("User already exist");
        }
        validation(user);
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
        if (!getUsers().contains(user)) {
            log.debug("{} ID User doesn't exist", user.getId());
            throw new UserNotExistException("ID User doesn't exist");
        }
        validation(user);
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
        List<User> users = jdbcTemplate
                .query("SELECT users.id FROM users", (rs, rowNum) -> rs.getInt("id"))
                .stream()
                .map(this::getUser)
                .toList();
        if (users.isEmpty()) {
            log.debug("Users no added");
            throw new UserNotExistException("Users no added");
        }
        return users;
    }

    @Override
    public User getUser(Integer id) {
        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM USERS WHERE ID = ?",
                (rs, rowNum) -> User.builder()
                        .email(rs.getString("EMAIL"))
                        .login(rs.getString("LOGIN"))
                        .username(rs.getString("USERNAME"))
                        .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                        .build(),
                id);
        if (user == null) throw new UserNotExistException("User not exist");
        return user;
    }

    private void validation(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            user.setUsername(user.getLogin());
            log.debug("Username of {} - empty", user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("{}: Birthday can't be in the future", user.getBirthday());
            throw new ValidationException("Birthday can't be in the future");
        }
    }
}
