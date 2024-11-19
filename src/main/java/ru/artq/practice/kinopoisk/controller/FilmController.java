package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 0;

    private Integer setId() {
        return ++id;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (films.containsValue(film)) {
            log.debug("Film: {} already exist", film.getName());
            throw new FilmAlreadyExistException("Film already exist");
        }
        if (films.containsKey(film.getId()) || film.getId() != null) {
            log.debug("ID Film: {} already exist", film.getId());
            throw new InvalidFilmIdException("ID Film already exist");
        }
        validation(film);
        film.setId(setId());
        films.put(film.getId(), film);
        log.info("Film {} added", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.debug("ID Film: {} doesn't exist", film.getId());
            throw new InvalidFilmIdException("ID Film doesn't exist");
        }
        validation(film);
        films.put(film.getId(), film);
        log.info("Film {} updated", film.getName());
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        if (films.isEmpty()) {
            log.debug("Films no added");
            throw new FilmNotExistException("Films no added");
        }
        return films.values();
    }

    @ExceptionHandler({FilmNotExistException.class, ValidationException.class})
    private ResponseEntity<String> handleFilmNotExistException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({FilmAlreadyExistException.class, InvalidFilmIdException.class})
    private ResponseEntity<String> handleFilmAlreadyExistException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        log.debug("{}", errors);
        return errors;
    }

    private void validation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.debug("Date can't be before 28 dec 1895: {} date", film.getReleaseDate());
            throw new ValidationException("Date can't be before 28 dec 1895");
        }
        if (film.getDuration().isZero() || film.getDuration().isNegative()) {
            log.debug("Duration must be positive: {} duration", film.getDuration());
            throw new ValidationException("Duration must be positive");
        }
    }
}
