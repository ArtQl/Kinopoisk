package ru.artq.practice.kinopoisk.exception;

public class ReviewNotExistException extends RuntimeException {
    public ReviewNotExistException(String message) {
        super(message);
    }

    public ReviewNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
