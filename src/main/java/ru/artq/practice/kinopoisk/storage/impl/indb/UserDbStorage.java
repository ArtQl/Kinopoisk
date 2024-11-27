package ru.artq.practice.kinopoisk.storage.impl.indb;

import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.inter.UserStorage;

import java.util.Collection;
import java.util.List;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public Collection<User> getUsers() {
        return List.of();
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }
}
