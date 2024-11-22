package ru.artq.practice.kinopoisk.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.exception.user.FriendsException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
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
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
        }
        if (user.getFriends().contains(userId) ||
                friend.getFriends().contains(user.getId())) {
            throw new FriendsException("Users already friends");
        } else {
            user.getFriends().add(friend.getId());
            friend.getFriends().add(user.getId());
        }
        return true;
    }

    public boolean removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (user.getFriends() == null
                || friend.getFriends() == null
                || !user.getFriends().contains(friend.getId())
                || !friend.getFriends().contains(user.getId()))
            throw new FriendsException("Users aren't friends");
        return user.getFriends().remove(friend.getId()) && friend.getFriends().remove(user.getId());
    }

    public Collection<User> getListFriends(Integer userId) {
        User user = userStorage.getUser(userId);
        if (user.getFriends() == null)
            throw new FriendsException("User has no friends");
        return user.getFriends().stream().map(userStorage::getUser).toList();
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = userStorage.getUser(userId);
        User otherUser = userStorage.getUser(otherId);
        Set<Integer> userSet = user.getFriends() == null ? new HashSet<>() : new HashSet<>(user.getFriends());
        Set<Integer> otherUserSet = otherUser.getFriends() == null ? new HashSet<>() : new HashSet<>(otherUser.getFriends());
        if (userSet.isEmpty() || otherUserSet.isEmpty())
            throw new FriendsException("Users has no common friends");
        userSet.retainAll(otherUserSet);
        return userSet.stream().map(userStorage::getUser).toList();
    }
}
