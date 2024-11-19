package ru.artq.practice.kinopoisk.exception.films;

public class InvalidFilmIdException extends RuntimeException {
    public InvalidFilmIdException(String message) {
        super(message);
    }
}
