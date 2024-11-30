package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;
import ru.artq.practice.kinopoisk.storage.UserStorage;
import ru.artq.practice.kinopoisk.util.Validation;

import java.util.Collection;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User addUser(User user) {
        Validation.validateUser(user);
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        Validation.validateUser(user);
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User getUser(Integer id) {
        return userStorage.getUser(id);
    }

    @Override
    public void clearUsers() {
        userStorage.clearUsers();
    }
}
