package ru.artq.practice.kinopoisk.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.UserService;

@SpringBootTest
@ActiveProfiles("in-memory")
public class InMemoryUserServiceAppTest extends UserServiceAppTest {
    @Autowired
    public InMemoryUserServiceAppTest(UserService userService) {
        super(userService);
    }
}
