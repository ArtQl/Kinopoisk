package ru.artq.practice.kinopoisk.exception.review;

public class ReviewIdExistException extends RuntimeException {
    public ReviewIdExistException(String message) {
        super(message);
    }

    public ReviewIdExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
