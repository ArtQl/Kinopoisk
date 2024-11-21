package ru.artq.practice.kinopoisk.storage.user;

import ru.artq.practice.kinopoisk.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();

}
