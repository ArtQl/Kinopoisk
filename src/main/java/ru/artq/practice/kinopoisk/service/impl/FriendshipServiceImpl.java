package ru.artq.practice.kinopoisk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.FriendshipService;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Override
    public void sendFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendshipStorage.sendFriendRequest(userId, friendId);
    }

    @Override
    public void acceptFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendshipStorage.acceptFriendRequest(userId, friendId);
    }

    @Override
    public void rejectFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendshipStorage.rejectFriendRequest(userId, friendId);
    }

    @Override
    public Collection<User> getFriends(Integer userId) {
        userStorage.getUser(userId);
        Collection<Friendship> friendships = friendshipStorage.getFriendshipsById(userId);
        if (friendships.isEmpty()) return List.of();
        return friendships.stream().map(f -> f.getUserId().equals(userId)
                        ? userStorage.getUser(f.getFriendId())
                        : userStorage.getUser(f.getUserId()))
                .toList();
    }

    @Override
    public Collection<User> getCommonFriends(Integer userId, Integer otherId) {
        validateUsers(userId, otherId);
        Collection<Integer> res = friendshipStorage.getCommonFriends(userId, otherId);
        return res.isEmpty() ? List.of() : res.stream().map(userStorage::getUser).toList();
    }

    private void validateUsers(Integer userId, Integer friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
    }
}
