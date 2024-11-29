package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.User;

import java.util.Collection;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUser(Integer id);
}
