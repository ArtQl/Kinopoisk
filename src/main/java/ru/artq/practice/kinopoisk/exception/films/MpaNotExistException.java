package ru.artq.practice.kinopoisk.exception.films;

public class MpaNotExistException extends RuntimeException {
    public MpaNotExistException(String message) {
        super(message);
    }

    public MpaNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
