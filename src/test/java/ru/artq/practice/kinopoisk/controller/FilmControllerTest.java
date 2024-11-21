package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.film.InMemoryFilmStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;
    Film film;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void start() {
        filmController = new FilmController(new InMemoryFilmStorage());
        film = Film.builder()
                .name("a")
                .description("aaaaaaaa10")
                .duration(Duration.ofMinutes(30))
                .releaseDate(LocalDate.of(2020,12,31))
                .build();
    }

    @Test
    void addFilm() {
        assertThrows(InvalidFilmIdException.class, () -> filmController.addFilm(Film.builder().name("a").description("a").duration(Duration.ofMinutes(30)).releaseDate(LocalDate.of(2020,12,31)).id(1).build()), "Have id film");

        Set<ConstraintViolation<Film>> violations = validator.validate(Film.builder().build());
        assertEquals(4, violations.size(), "empty film");

        assertThrows(ValidationException.class, () -> filmController.addFilm(Film.builder().name("a").description("a")
                .duration(Duration.ofMinutes(-21))
                .releaseDate(LocalDate.of(2020,12,31))
                .build()), "Negative duration");

        assertEquals(0, validator.validate(film).size());
        assertDoesNotThrow(() -> filmController.addFilm(film), "Fill film");
    }

    @Test
    void updateFilm() {
        filmController.addFilm(film);
        film.setId(12);
        assertThrows(InvalidFilmIdException.class, () -> filmController.updateFilm(film));
    }

    @Test
    void getFilms() {
        assertThrows(FilmNotExistException.class, () -> filmController.getFilms());
        filmController.addFilm(film);
        assertEquals(1 , filmController.getFilms().size());
    }
}