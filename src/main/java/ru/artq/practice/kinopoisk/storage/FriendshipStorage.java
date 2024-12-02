package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Friendship;

import java.util.Collection;

public interface FriendshipStorage {
    Boolean sendFriendRequest(Integer userId, Integer friendId);

    Boolean acceptFriendRequest(int userId, int friendId);

    Boolean rejectFriendRequest(int userId, int friendId);

    Collection<Friendship> getFriendshipsById(Integer userId);

    Collection<Integer> getCommonFriends(Integer userId, Integer otherUserId);

    Friendship findFriendship(int userId, int friendId);
}
