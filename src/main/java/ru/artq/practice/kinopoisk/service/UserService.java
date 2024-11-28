package ru.artq.practice.kinopoisk.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage,
                       FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public Boolean sendFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        return friendshipStorage.sendFriendRequest(userId, friendId);
    }

    public Boolean acceptFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendshipStorage.acceptFriendRequest(userId, friendId);
        return true;
    }

    public Boolean rejectFriendRequest(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendshipStorage.rejectFriendRequest(userId, friendId);
        return true;
    }

    public Collection<User> getListFriends(Integer userId) {
        userStorage.getUser(userId);
        Collection<Friendship> friendships = friendshipStorage.getFriendshipsById(userId);
        if (friendships.isEmpty()) return List.of();
        return friendships.stream().map(f -> f.getUserId().equals(userId)
                        ? userStorage.getUser(f.getFriendId())
                        : userStorage.getUser(f.getUserId()))
                .toList();
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        validateUsers(userId, otherId);
        List<Integer> res = friendshipStorage.getCommonFriends(userId, otherId);
        if (res.isEmpty()) return List.of();
        return res.stream().map(userStorage::getUser).toList();
    }

    private void validateUsers(Integer userId, Integer friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
    }
}
