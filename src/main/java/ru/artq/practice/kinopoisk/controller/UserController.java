package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.service.impl.UserServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userServiceImpl.getUserStorage().addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userServiceImpl.getUserStorage().updateUser(user);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userServiceImpl.getUserStorage().getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userServiceImpl.getUserStorage().getUser(id);
    }

//    @PutMapping("/{id}/friends/{friendId}")
//    public Boolean addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
//        return userServiceImpl.sendFriendRequest(id, friendId);
//    }
//
//    @DeleteMapping("/{id}/friends/{friendId}")
//    public Boolean removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
//        userServiceImpl.rejectFriendRequest(id, friendId);
//        return true;
//    }
//
//    @GetMapping("/{id}/friends")
//    public Collection<User> getFriends(@PathVariable Integer id){
//        return userServiceImpl.getListFriends(id);
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public Collection<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
//        return userServiceImpl.getCommonFriends(id, otherId);
//    }
}
