package ru.artq.practice.kinopoisk.exception.films;

public class GenreNotExistException extends RuntimeException {
    public GenreNotExistException(String message) {
        super(message);
    }

    public GenreNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
