package ru.artq.practice.kinopoisk.exception.films;

public class FilmNotExistException extends RuntimeException {
    public FilmNotExistException(String message) {
        super(message);
    }

    public FilmNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
