package ru.artq.practice.kinopoisk.user;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
abstract class UserServiceAppTest {
    private final UserService userService;

    private User user = User.builder()
            .username("Art").email("ased@mail.ru").login("Arte")
            .birthday(LocalDate.of(2012, 12, 12))
            .build();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @AfterEach
    void end() {
        userService.clearUsers();
    }

    @Test
    void addUser() {
        assertDoesNotThrow(() -> userService.addUser(user), "User fill");

        assertEquals(3, validator.validate(User.builder().build()).size(), "Empty User");

        user = User.builder()
                .username("Art").email("asedmail.ru").login("Arte")
                .birthday(LocalDate.of(2012, 12, 12))
                .build();
        assertEquals(1, validator.validate(user).size(), "Wrong email");

        user = User.builder()
                .email("ased@mail.ru").login("Arte")
                .birthday(LocalDate.of(2012, 12, 12))
                .build();
        assertEquals(0, validator.validate(userService.addUser(user)).size(), "No error");
        assertEquals("Arte", (userService.getUsers().stream().toList().getLast()).getUsername(), "Change name");
    }

    @Test
    void updateUser() {
        userService.addUser(user);
        user.setId(12);
        assertThrows(InvalidUserIdException.class, () -> userService.updateUser(user));
    }

    @Test
    void getUsers() {
        assertThrows(UserNotExistException.class, userService::getUsers);
        userService.addUser(user);
        assertEquals(1, userService.getUsers().size());
    }
}