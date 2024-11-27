package ru.artq.practice.kinopoisk.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.inter.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 0;

    private Integer setId() {
        return ++id;
    }

    @Override
    public Film addFilm(Film film) {
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

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.debug("ID Film: {} doesn't exist", film.getId());
            throw new InvalidFilmIdException("ID Film doesn't exist");
        }
        validation(film);
        films.put(film.getId(), film);
        log.info("Film {} updated", film.getName());
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        if (films.isEmpty()) {
            log.debug("Films no added");
            throw new FilmNotExistException("Films no added");
        }
        return films.values();
    }

    @Override
    public Film getFilm(Integer id) {
        Film film = films.getOrDefault(id, null);
        if (film == null) throw new FilmNotExistException("Film doesn't exist");
        return film;
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
