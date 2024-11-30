package ru.artq.practice.kinopoisk.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@Profile("in-memory")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    private Integer setId() {
        if (users.isEmpty()) id = 0;
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

    @Override
    public User getUser(Integer id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(() -> new UserNotExistException("User with id: " + id + " not found"));
    }

    @Override
    public void clearUsers() {
        users.clear();
    }
}
