package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Event;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;

import java.util.Collection;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUser(Integer id);

    Collection<Film> recommendations(Integer id);

    Collection<Integer> getUserLikes(Integer userId);

    Collection<Film> getCommonFilms(Integer userId, Integer otherUserId);

    void sendFriendRequest(Integer userId, Integer friendId);

    void acceptFriendRequest(Integer userId, Integer friendId);

    void rejectFriendRequest(Integer userId, Integer friendId);

    Collection<User> getFriends(Integer userId);

    Collection<User> getCommonFriends(Integer userId, Integer otherId);

    Collection<Event> getEventsUser(Integer id);
}
