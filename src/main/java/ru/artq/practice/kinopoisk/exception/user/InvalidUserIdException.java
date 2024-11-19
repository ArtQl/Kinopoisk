package ru.artq.practice.kinopoisk.exception.user;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}
