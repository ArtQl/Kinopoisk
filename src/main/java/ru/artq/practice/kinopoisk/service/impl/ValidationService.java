package ru.artq.practice.kinopoisk.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.model.MPA;
import ru.artq.practice.kinopoisk.model.User;

import java.time.LocalDate;

@Slf4j
public class ValidationService {

    public static void validateFilm(Film film) {
        if (film == null)
            throw new FilmNotExistException("Film is null");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Invalid release date for film {}: {}", film.getName(), film.getReleaseDate());
            throw new ValidationException("Date can't be before 28 dec 1895");
        }
        if (film.getDuration() == null
                || film.getDuration().isZero()
                || film.getDuration().isNegative()) {
            log.debug("Invalid duration for film {}: {}", film.getName(), film.getDuration());
            throw new ValidationException("Duration must be positive");
        }
    }

    public static void validateUser(User user) {
        if (user == null)
            throw new UserNotExistException("User is null");
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            user.setUsername(user.getLogin());
            log.debug("Username of {} - empty", user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("{}: Birthday can't be in the future", user.getBirthday());
            throw new ValidationException("Birthday can't be in the future");
        }
    }

    public static Genre validateGenre(String genre) {
        Genre genreEnum;
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        try {
            genreEnum = Genre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Genre is not correct", e);
        }
        return genreEnum;
    }

    public static MPA validateMPA(String mpa) {
        MPA mpaEnum;
        if (mpa == null)
            throw new IllegalArgumentException("MPA cannot be null");
        try {
            mpaEnum = MPA.valueOf(mpa.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("MPA is not correct", e);
        }
        return mpaEnum;
    }
}
