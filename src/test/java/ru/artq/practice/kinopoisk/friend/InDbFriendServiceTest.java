package ru.artq.practice.kinopoisk.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.impl.FriendshipServiceImpl;

@SpringBootTest
@ActiveProfiles("db")
public class InDbFriendServiceTest extends FriendServiceTest {
    @Autowired
    public InDbFriendServiceTest(FriendshipServiceImpl friendshipService) {
        super(friendshipService);
    }
}
