package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Friendship;

import java.util.Collection;

public interface FriendshipStorage {
    void sendFriendRequest(Integer userId, Integer friendId);

    void acceptFriendRequest(int userId, int friendId);

    void rejectFriendRequest(int userId, int friendId);

    Collection<Friendship> getFriendshipsById(Integer userId);

    Collection<Integer> getCommonFriends(Integer userId, Integer otherUserId);

    Friendship findFriendship(int userId, int friendId);

    void clear();
}
