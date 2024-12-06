package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;
import ru.artq.practice.kinopoisk.storage.LikeStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;
    private final FilmStorage filmStorage;
    private final FriendshipStorage friendshipStorage;

    @Override
    public User addUser(User user) {
        ValidationService.validateUser(user);
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        ValidationService.validateUser(user);
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User getUser(Integer id) {
        return userStorage.getUser(id);
    }

    @Override
    public Collection<Film> recommendations(Integer id) {
        //todo
        getFriends(id);
        return userStorage.recommendations(id);
    }

    @Override
    public Collection<Integer> getUserLikes(Integer userId) {
        userStorage.getUser(userId);
        return likeStorage.getUserLikes(userId);
    }

    @Override
    public Collection<Film> getCommonFilms(Integer userId, Integer otherUserId) {
        userStorage.getUser(userId);
        userStorage.getUser(otherUserId);
        Collection<Integer> res = likeStorage.getCommonFilms(otherUserId, userId);
        return res.isEmpty() ? List.of() : res.stream().map(filmStorage::getFilm).toList();
    }


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
