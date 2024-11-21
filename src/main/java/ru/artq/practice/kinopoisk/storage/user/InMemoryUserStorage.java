package ru.artq.practice.kinopoisk.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    private Integer setId() {
        return ++id;
    }

    @Override
    public User addUser(User user) {
        if (users.containsValue(user)) {
            log.debug("{} already exist", user);
            throw new UserAlreadyExistException("User already exist");
        }
        if (users.containsKey(user.getId())) {
            log.debug("{} ID user already exist", user.getId());
            throw new InvalidUserIdException("ID user already exist");
        }
        validation(user);
        user.setId(setId());
        users.put(user.getId(), user);
        log.info("User added: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.debug("{} ID User doesn't exist", user.getId());
            throw new InvalidUserIdException("ID User doesn't exist");
        }
        validation(user);
        users.put(user.getId(), user);
        log.info("User updated: {}", user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        if (users.isEmpty()) {
            log.debug("Users no added");
            throw new UserNotExistException("Users no added");
        }
        return users.values();
    }

    private void validation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Username of {} - empty", user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("{}: Birthday can't be in the future", user.getBirthday());
            throw new ValidationException("Birthday can't be in the future");
        }
    }
}
