package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Event;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/recommendations")
    public Collection<Film> recommendations(@PathVariable Integer id) {
        return userService.recommendations(id);
    }

    @GetMapping("/likes/{userId}")
    public Collection<Integer> getUserLikes(@PathVariable Integer userId) {
        return userService.getUserLikes(userId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Integer id) {
        return userService.getFriends(id);
    }

    @PostMapping("/{id}/friends/{friendId}")
    public void sendFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.sendFriendRequest(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void acceptFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.acceptFriendRequest(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void rejectFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.rejectFriendRequest(id, friendId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}/friends")
    public Collection<User> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer friendId) {
        return userService.getCommonFriends(userId, friendId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}/films")
    public Collection<Film> getCommonFilms(@PathVariable Integer userId,
                                           @PathVariable Integer friendId) {
        return userService.getCommonFilms(userId, friendId);
    }

    @GetMapping("/{id}/feed")
    public Collection<Event> getEventsUser(@PathVariable Integer id) {
        //todo
        return null;
    }
}
