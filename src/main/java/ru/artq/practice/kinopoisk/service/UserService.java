package ru.artq.practice.kinopoisk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public boolean addFriend(Integer idUser, User user) {
        return false;
    }

    public boolean removeFriend(Integer idUser, User user) {
        return false;
    }

    public List<User> getListFriends() {
        return List.of();
    }
}
