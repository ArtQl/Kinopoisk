package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;
    User user;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @BeforeEach
    void start() {
        userController = new UserController(new InMemoryUserStorage());
        user = User.builder()
                .name("Art").email("ased@mail.ru").login("Arte")
                .birthday(LocalDate.of(2012, 12, 12))
                .build();
    }

    @Test
    void addUser() {
        assertDoesNotThrow(() -> userController.addUser(user), "User fill");

        assertEquals(3, validator.validate(User.builder().build()).size(), "Empty User");

        user = User.builder()
                .name("Art").email("asedmail.ru").login("Arte")
                .birthday(LocalDate.of(2012, 12, 12))
                .build();
        assertEquals(1, validator.validate(user).size(), "Wrong email");

        user = User.builder()
                .email("ased@mail.ru").login("Arte")
                .birthday(LocalDate.of(2012, 12, 12))
                .build();
        assertEquals(0, validator.validate(userController.addUser(user)).size(), "No error");
        assertEquals("Arte", (userController.getUsers().stream().toList().getLast()).getName(), "Change name");
    }

    @Test
    void updateUser() {
        userController.addUser(user);
        user.setId(12);
        assertThrows(InvalidUserIdException.class, () -> userController.updateUser(user));
    }

    @Test
    void getUsers() {
        assertThrows(UserNotExistException.class, () -> userController.getUsers());
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size());
    }
}