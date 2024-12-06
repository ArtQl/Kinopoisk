package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUser(Integer id);

    void clear();

    Collection<Film> recommendations(Integer id);
}
