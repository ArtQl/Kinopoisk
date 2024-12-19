package ru.artq.practice.kinopoisk.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.artq.practice.kinopoisk.service.UserService;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

@SpringBootTest
@ActiveProfiles("db")
public class InDbFriendControllerTest extends FriendControllerTest {
    @Autowired
    public InDbFriendControllerTest(UserStorage userStorage, UserService userService, FriendshipStorage friendshipStorage, MockMvc mockMvc) {
        super(userStorage, userService, friendshipStorage, mockMvc);
    }
}
