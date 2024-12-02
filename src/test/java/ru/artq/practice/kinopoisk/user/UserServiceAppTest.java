package ru.artq.practice.kinopoisk.user;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class UserServiceAppTest {
    private final UserService userService;

    private final User user = User.builder()
            .username("Art").email("ased@mail.ru").login("Arte")
            .birthday(LocalDate.of(2012, 12, 12))
            .build();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void addUser() {
        assertThrows(UserNotExistException.class, userService::getUsers);
        userService.addUser(user);
        assertEquals(1, userService.getUsers().size());

        user.setId(12);
        assertThrows(InvalidUserIdException.class, () -> userService.updateUser(user));

        assertEquals(0, validator.validate(
                userService.addUser(
                        User.builder().email("ased@mail.ru").login("Arte")
                                .birthday(LocalDate.of(2012, 12, 12))
                                .build())
        ).size(), "No error");

        assertEquals(3, validator.validate(
                User.builder().build()).size(), "Empty User");

        assertEquals(1, validator.validate(
                User.builder()
                        .username("Art").email("asedmail.ru").login("Arte")
                        .birthday(LocalDate.of(2012, 12, 12))
                        .build()
        ).size(), "Wrong email");

        assertEquals("Arte",
                (userService.getUsers().stream().toList().getLast())
                        .getUsername(), "Change name");
    }
}