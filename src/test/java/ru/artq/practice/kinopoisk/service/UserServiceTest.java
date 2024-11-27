package ru.artq.practice.kinopoisk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.artq.practice.kinopoisk.exception.user.FriendsException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    UserService userService;

    @BeforeEach
    void start() {
        userService = new UserService(new InMemoryUserStorage());
        for (int i = 1; i <= 10; i++) {
            userService.getUserStorage().addUser(User.builder().email("adf@mail.ru").login("a" + i).birthday(LocalDate.now()).build());
        }
    }

    @Test
    void addFriends() {
        assertThrows(UserNotExistException.class, () -> userService.addFriend(0, 99), "No that user");
        assertThrows(UserNotExistException.class, () -> userService.addFriend(9, 0), "No that user");

        userService.addFriend(1, 2);
        assertTrue(userService.getUserStorage().getUser(1).getFriends().contains(2), "add friend 1 to 2");
        assertTrue(userService.getUserStorage().getUser(2).getFriends().contains(1), "add friend 2 to 1");

        assertThrows(FriendsException.class, () -> userService.addFriend(1, 2));
        assertThrows(FriendsException.class, () -> userService.addFriend(2, 1));
    }

    @Test
    void removeFriends() {
        userService.addFriend(1, 2);
        assertTrue(userService.removeFriend(1, 2));
        assertTrue(userService.getUserStorage().getUser(1).getFriends().isEmpty(), "Empty");
        assertTrue(userService.getUserStorage().getUser(2).getFriends().isEmpty(), "Empty");

        assertThrows(FriendsException.class, () -> userService.removeFriend(1, 5), "Empty");
    }

    @Test
    void getListFriends() {
        assertThrows(FriendsException.class, () -> userService.getListFriends(1), "no friends");
        assertThrows(UserNotExistException.class, () -> userService.getListFriends(99), "No user");
        userService.addFriend(1, 2);
        userService.addFriend(1, 3);
        assertEquals(2, userService.getListFriends(1).size());
        assertEquals(1, userService.getListFriends(2).size());
        assertEquals(1, userService.getListFriends(3).size());
    }

    @Test
    void getCommonFriends() {
        userService.addFriend(1,2);
        userService.addFriend(1,3);
        userService.addFriend(1,4);

        userService.addFriend(2, 3);
        userService.addFriend(2, 5);

        userService.addFriend(3, 5);

        assertThrows(FriendsException.class, () -> userService.getCommonFriends(1,6), "no common");

        assertEquals(userService.getUserStorage().getUser(3), userService.getCommonFriends(1,2).getFirst());
        assertEquals(userService.getUserStorage().getUser(3), userService.getCommonFriends(2,1).getFirst());

        assertEquals(2, userService.getCommonFriends(2,3).size());
    }
}
