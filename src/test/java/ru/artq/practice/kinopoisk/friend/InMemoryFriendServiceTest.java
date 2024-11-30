package ru.artq.practice.kinopoisk.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.impl.FriendshipServiceImpl;

@SpringBootTest
@ActiveProfiles("in-memory")
public class InMemoryFriendServiceTest extends FriendServiceTest {
    @Autowired
    public InMemoryFriendServiceTest(FriendshipServiceImpl friendshipService) {
        super(friendshipService);
    }
}
