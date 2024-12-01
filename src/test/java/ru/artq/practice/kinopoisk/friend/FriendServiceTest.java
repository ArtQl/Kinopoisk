package ru.artq.practice.kinopoisk.friend;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.impl.FriendshipServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class FriendServiceTest {
    private final FriendshipServiceImpl friendshipService;

    @Test
    void test() {
        System.out.println(friendshipService.getUserStorage().getUsers());
    }

    @BeforeEach
    void start() {
        for (int i = 1; i <= 10; i++) {
            friendshipService.getUserStorage().addUser(
                    User.builder()
                            .email("adf@mail.ru")
                            .login("a" + i)
                            .username("A" + i)
                            .birthday(LocalDate.now())
                            .build());
        }
    }

    @AfterEach
    void end() {
        friendshipService.clearFriends();
    }

    @Test
    void sendFriendsRequest() {
        assertThrows(UserNotExistException.class,
                () -> friendshipService.sendFriendRequest(1, 99),
                "No that user");
        assertThrows(UserNotExistException.class,
                () -> friendshipService.sendFriendRequest(88, 7),
                "No that user");

        assertTrue(friendshipService.sendFriendRequest(1, 2));
        assertNotNull(friendshipService.getFriendshipStorage().findFriendship(1, 2),
                "add friend 1 to 2");
        assertThrows(FriendshipException.class,
                () -> friendshipService.getFriendshipStorage().findFriendship(2, 1),
                "friendship not found");
        assertThrows(FriendshipException.class,
                () -> friendshipService.getFriendshipStorage().findFriendship(1, 99));

        assertFalse(friendshipService.sendFriendRequest(1, 2));
        assertFalse(friendshipService.sendFriendRequest(2, 1));
    }

    @Test
    void rejectFriendsRequest() {
        friendshipService.sendFriendRequest(1, 2);
        assertTrue(friendshipService.rejectFriendRequest(1, 2));
        assertEquals(FriendshipStatus.REJECTED, friendshipService.getFriendshipStorage().findFriendship(1,2).getStatus());
        assertThrows(FriendshipException.class, () -> friendshipService.rejectFriendRequest(1, 5), "Empty");
        assertThrows(FriendshipException.class, () -> friendshipService.rejectFriendRequest(2, 1), "Empty");
    }

    @Test
    void acceptFriendsRequest() {
        friendshipService.sendFriendRequest(1, 2);
        assertTrue(friendshipService.acceptFriendRequest(1, 2));
        assertEquals(FriendshipStatus.ACCEPTED, friendshipService.getFriendshipStorage().findFriendship(1,2).getStatus());
        assertThrows(FriendshipException.class, () -> friendshipService.acceptFriendRequest(1, 5), "Empty");
        assertThrows(FriendshipException.class, () -> friendshipService.acceptFriendRequest(2, 1), "Empty");
    }

    @Test
    void getFriends() {
        friendshipService.sendFriendRequest(1, 2);
        friendshipService.sendFriendRequest(1, 3);

        assertTrue(friendshipService.getFriends(1).isEmpty());
        assertTrue(friendshipService.getFriends(2).isEmpty());
        assertThrows(UserNotExistException.class, () -> friendshipService.getFriends(99), "No user");

        friendshipService.acceptFriendRequest(1, 2);
        friendshipService.rejectFriendRequest(1, 3);

        assertEquals(1, friendshipService.getFriends(1).size(), "accept");
        assertEquals(1, friendshipService.getFriends(2).size(), "accept");
        assertEquals(0, friendshipService.getFriends(3).size(), "reject");
    }

    @Test
    void getCommonFriends() {
        friendshipService.sendFriendRequest(1,2);
        friendshipService.sendFriendRequest(1,3);
        friendshipService.sendFriendRequest(1,4);
        friendshipService.acceptFriendRequest(1, 2);
        friendshipService.acceptFriendRequest(1, 3);
        friendshipService.acceptFriendRequest(1, 4);

        friendshipService.sendFriendRequest(2, 3);
        friendshipService.sendFriendRequest(2, 5);
        friendshipService.acceptFriendRequest(2,3);
        friendshipService.acceptFriendRequest(2,5);

        friendshipService.sendFriendRequest(3, 5);
        friendshipService.acceptFriendRequest(3, 5);

        assertTrue(friendshipService.getCommonFriends(1,6).isEmpty());

        assertEquals(friendshipService.getUserStorage().getUser(3),
                friendshipService.getCommonFriends(1,2)
                        .stream().toList().getFirst());
        assertEquals(friendshipService.getUserStorage().getUser(3),
                friendshipService.getCommonFriends(2,1)
                        .stream().toList().getFirst());

        assertEquals(2, friendshipService.getCommonFriends(2,3).size());
    }
}
