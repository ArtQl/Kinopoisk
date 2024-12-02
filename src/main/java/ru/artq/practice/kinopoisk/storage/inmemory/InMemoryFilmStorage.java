package ru.artq.practice.kinopoisk.storage.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.*;

@Slf4j
@Component
@Profile("in-memory")
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
        return Optional.ofNullable(films.get(id))
                .orElseThrow(() -> new FilmNotExistException("Film doesn't exist"));
    }

    @Override
    public Collection<Film> getTopFilmByYear(Integer year) {
        return films.values().stream()
                .filter(film -> year.equals(film.getReleaseDate().getYear())).toList();
    }
}
