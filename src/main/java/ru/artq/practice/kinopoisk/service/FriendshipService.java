package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.User;

import java.util.Collection;

public interface FriendshipService {

    void sendFriendRequest(Integer userId, Integer friendId);

    void acceptFriendRequest(Integer userId, Integer friendId);

    void rejectFriendRequest(Integer userId, Integer friendId);

    Collection<User> getFriends(Integer userId);

    Collection<User> getCommonFriends(Integer userId, Integer otherId);
}
