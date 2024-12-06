package ru.artq.practice.kinopoisk.exception;

public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException(String message) {
        super(message);
    }

    public MpaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
