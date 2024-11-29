package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.FriendshipService;
import ru.artq.practice.kinopoisk.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    public UserController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

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

    @PutMapping("/{id}/friends/{friendId}")
    public Boolean sendFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        return friendshipService.sendFriendRequest(id, friendId);
    }

    @PostMapping("/{id}/friends/{friendId}")
    public Boolean acceptFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        return friendshipService.acceptFriendRequest(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Boolean rejectFriendRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        return friendshipService.rejectFriendRequest(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Integer id) {
        return friendshipService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return friendshipService.getCommonFriends(id, otherId);
    }
}
