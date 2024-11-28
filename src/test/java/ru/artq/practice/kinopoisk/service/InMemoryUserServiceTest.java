package ru.artq.practice.kinopoisk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.impl.inmemory.InMemoryFriendshipStorage;
import ru.artq.practice.kinopoisk.storage.impl.inmemory.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserServiceTest {
    UserService userService;

    @BeforeEach
    void start() {
        userService = new UserService(new InMemoryUserStorage(), new InMemoryFriendshipStorage());
        for (int i = 1; i <= 10; i++) {
            userService.getUserStorage().addUser(User.builder().email("adf@mail.ru").login("a" + i).birthday(LocalDate.now()).build());
        }
    }

    @Test
    void sendFriendsRequest() {
        assertThrows(UserNotExistException.class, () -> userService.sendFriendRequest(1, 99), "No that user");
        assertThrows(UserNotExistException.class, () -> userService.sendFriendRequest(88, 7), "No that user");

        assertTrue(userService.sendFriendRequest(1, 2));
        assertNotNull(userService.getFriendshipStorage().findFriendship(1, 2), "add friend 1 to 2");
        assertNotNull(userService.getFriendshipStorage().findFriendship(2, 1), "add friend 2 to 1");
        assertThrows(FriendshipException.class, () -> userService.getFriendshipStorage().findFriendship(1, 99));
        assertThrows(FriendshipException.class, () -> userService.getFriendshipStorage().findFriendship(99, 1));

        assertFalse(userService.sendFriendRequest(1, 2));
        assertFalse(userService.sendFriendRequest(2, 1));
    }

    @Test
    void rejectFriendsRequest() {
        userService.sendFriendRequest(1, 2);
        assertThrows(IllegalArgumentException.class, () -> userService.rejectFriendRequest(1, 2), "id not correct");
        assertTrue(userService.rejectFriendRequest(2, 1));
        assertEquals(FriendshipStatus.REJECTED, userService.getFriendshipStorage().findFriendship(2,1).getStatus());
        assertThrows(FriendshipException.class, () -> userService.rejectFriendRequest(1, 5), "Empty");
        assertThrows(FriendshipException.class, () -> userService.rejectFriendRequest(5, 1), "Empty");
    }

    @Test
    void acceptFriendsRequest() {
        userService.sendFriendRequest(1, 2);
        assertThrows(IllegalArgumentException.class, () -> userService.acceptFriendRequest(1, 2), "id not correct");
        assertTrue(userService.acceptFriendRequest(2, 1));
        assertEquals(FriendshipStatus.ACCEPTED, userService.getFriendshipStorage().findFriendship(1,2).getStatus());
        assertThrows(FriendshipException.class, () -> userService.acceptFriendRequest(1, 5), "Empty");
        assertThrows(FriendshipException.class, () -> userService.acceptFriendRequest(5, 1), "Empty");
    }

    @Test
    void getListFriends() {
        userService.sendFriendRequest(1, 2);
        userService.sendFriendRequest(1, 3);

        assertTrue(userService.getListFriends(1).isEmpty());
        assertTrue(userService.getListFriends(2).isEmpty());
        assertThrows(UserNotExistException.class, () -> userService.getListFriends(99), "No user");

        userService.acceptFriendRequest(2, 1);
        userService.rejectFriendRequest(3, 1);

        assertEquals(1, userService.getListFriends(1).size());
        assertEquals(1, userService.getListFriends(2).size());
        assertEquals(0, userService.getListFriends(3).size());
    }

    @Test
    void getCommonFriends() {
        userService.sendFriendRequest(1,2);
        userService.sendFriendRequest(1,3);
        userService.sendFriendRequest(1,4);
        userService.acceptFriendRequest(2, 1);
        userService.acceptFriendRequest(3, 1);
        userService.acceptFriendRequest(4, 1);

        userService.sendFriendRequest(2, 3);
        userService.sendFriendRequest(2, 5);
        userService.acceptFriendRequest(3,2);
        userService.acceptFriendRequest(5,2);

        userService.sendFriendRequest(3, 5);
        userService.acceptFriendRequest(5, 3);

        assertTrue(userService.getCommonFriends(1,6).isEmpty());

        assertEquals(userService.getUserStorage().getUser(3), userService.getCommonFriends(1,2).getFirst());
        assertEquals(userService.getUserStorage().getUser(3), userService.getCommonFriends(2,1).getFirst());

        assertEquals(2, userService.getCommonFriends(2,3).size());
    }
}
