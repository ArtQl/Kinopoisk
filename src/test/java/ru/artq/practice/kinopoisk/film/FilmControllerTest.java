package ru.artq.practice.kinopoisk.film;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.artq.practice.kinopoisk.controller.FilmController;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class FilmControllerTest {
    private final FilmController filmController;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private final Film film = Film.builder()
            .name("a").description("1234567891")
            .duration(Duration.ofMinutes(30))
            .releaseDate(LocalDate.of(2020, 12, 31))
            .build();

    @Test
    void addFilm() {
        assertThrows(FilmNotExistException.class, filmController::getFilms);
        assertDoesNotThrow(() -> filmController.addFilm(film), "Fill film");
        assertEquals(1, filmController.getFilms().size());

        film.setId(99);
        assertThrows(InvalidFilmIdException.class, () -> filmController.updateFilm(film));

        assertEquals(4, validator.validate(Film.builder().build()).size(), "empty film");
        assertThrows(ValidationException.class,
                () -> filmController.addFilm(Film.builder().name("a").description("a")
                .duration(Duration.ofMinutes(-21)).releaseDate(LocalDate.of(2020, 12, 31))
                .build()), "Negative duration");

        assertEquals(0, validator.validate(film).size());

        assertThrows(FilmNotExistException.class, () -> filmController.addFilm(null));
        assertThrows(FilmAlreadyExistException.class, () -> filmController.addFilm(filmController.getFilmById(1)), "Already Exist");

        Film film1 = Film.builder().id(123).releaseDate(LocalDate.now()).name("dfa").duration(Duration.ofMinutes(10)).build();
        assertThrows(InvalidFilmIdException.class, () -> filmController.addFilm(film1), "ID Exist");
    }
}