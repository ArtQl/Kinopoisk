package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;

@Getter
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("inDbUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
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
}
