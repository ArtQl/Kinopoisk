package ru.artq.practice.kinopoisk.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.user.FriendsException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public boolean addFriend(Integer userId, Integer friendId) {
        validationId(userId, friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (!user.getFriends().add(friend.getId())
                && !friend.getFriends().add(user.getId()))
            throw new FriendsException("Users already friends");
        return true;
    }

    public boolean removeFriend(Integer userId, Integer friendId) {
        validationId(userId, friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (!user.getFriends().remove(friend.getId())
                && !friend.getFriends().remove(user.getId()))
            throw new FriendsException("Users aren't friends");
        return true;
    }

    public Collection<User> getListFriends(Integer userId) {
        return userStorage.getUser(userId).getFriends().stream().map(userStorage::getUser).toList();
    }

    public Collection<User> getCommonFriends(Integer userId, Integer otherId) {
        validationId(userId, otherId);
        Set<Integer> userSet = userStorage.getUser(userId).getFriends();
        Set<Integer> otherUserSet = userStorage.getUser(otherId).getFriends();
        if (userSet == null || otherUserSet == null)
            return List.of();
        userSet.retainAll(otherUserSet);
        return userSet.stream().map(userStorage::getUser).toList();
    }

    private void validationId(Integer userId, Integer friendId) {
        if (userId == null || friendId == null || userId <= 0 || friendId <= 0)
            throw new ValidationException("UserID or FriendID entered incorrectly");
    }
}
