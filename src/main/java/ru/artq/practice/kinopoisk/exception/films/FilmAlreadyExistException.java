package ru.artq.practice.kinopoisk.exception.films;

public class FilmAlreadyExistException extends RuntimeException {
    public FilmAlreadyExistException(String message) {
        super(message);
    }
}
