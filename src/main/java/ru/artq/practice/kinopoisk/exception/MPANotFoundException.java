package ru.artq.practice.kinopoisk.exception;

public class MPANotFoundException extends RuntimeException {
    public MPANotFoundException(String message) {
        super(message);
    }

    public MPANotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
