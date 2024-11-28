package ru.artq.practice.kinopoisk.storage.impl;

import lombok.extern.slf4j.Slf4j;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;

import java.time.LocalDate;

@Slf4j
public class Validation {
    public static void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Invalid release date for film {}: {}", film.getName(), film.getReleaseDate());
            throw new ValidationException("Date can't be before 28 dec 1895");
        }
        if (film.getDuration().isZero() || film.getDuration().isNegative()) {
            log.debug("Invalid duration for film {}: {}", film.getName(), film.getDuration());
            throw new ValidationException("Duration must be positive");
        }
    }

    public static  void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            user.setUsername(user.getLogin());
            log.debug("Username of {} - empty", user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("{}: Birthday can't be in the future", user.getBirthday());
            throw new ValidationException("Birthday can't be in the future");
        }
    }
}
