package ru.artq.practice.kinopoisk.exception.films;

public class GenreIdException extends RuntimeException {
    public GenreIdException(String message) {
        super(message);
    }

    public GenreIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
