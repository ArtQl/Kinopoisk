package ru.artq.practice.kinopoisk.film;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class FilmServiceAppTest {
    private final FilmService filmService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    Film film = Film.builder()
            .name("a")
            .description("1234567891")
            .duration(Duration.ofMinutes(30))
            .releaseDate(LocalDate.of(2020, 12, 31))
            .build();

    @Test
    void addFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(Film.builder().build());
        assertEquals(4, violations.size(), "empty film");

        assertThrows(ValidationException.class, () -> filmService.addFilm(Film.builder()
                .name("a").description("a")
                .duration(Duration.ofMinutes(-21))
                .releaseDate(LocalDate.of(2020, 12, 31))
                .build()), "Negative duration");

        assertEquals(0, validator.validate(film).size());
        assertDoesNotThrow(() -> filmService.addFilm(film), "Fill film");

        assertThrows(FilmNotExistException.class, () -> filmService.addFilm(null));
        assertThrows(FilmAlreadyExistException.class, () -> filmService.addFilm(filmService.getFilmById(1)), "Already Exist");

        Film film1 = Film.builder().id(123).releaseDate(LocalDate.now()).name("dfa").duration(Duration.ofMinutes(10)).build();
        assertThrows(InvalidFilmIdException.class, () -> filmService.addFilm(film1), "ID Exist");
        System.out.println(filmService.getFilms());
    }

    @Test
    void updateFilm() {
        filmService.addFilm(film);
        film.setId(99);
        assertThrows(InvalidFilmIdException.class, () -> filmService.updateFilm(film));
    }

    @Test
    void getFilms() {
        filmService.clearFilms();
        assertThrows(FilmNotExistException.class, filmService::getFilms);
        filmService.addFilm(film);
        assertEquals(1, filmService.getFilms().size());
    }
}
