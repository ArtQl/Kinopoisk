package ru.artq.practice.kinopoisk.exception.user;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
