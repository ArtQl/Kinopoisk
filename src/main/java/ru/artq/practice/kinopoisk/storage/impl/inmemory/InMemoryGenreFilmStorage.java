package ru.artq.practice.kinopoisk.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.*;

@Slf4j
@Component
@Profile("in-memory")
public class InMemoryGenreFilmStorage implements GenreFilmStorage {
    private final Map<Integer, Set<Genre>> genreFilm = new HashMap<>();

    @Override
    public Collection<Genre> getAllGenreFilm(Integer filmId) {
        return Optional.ofNullable(genreFilm.get(filmId)).orElse(Set.of());
    }

    @Override
    public Boolean addGenreToFilm(Integer filmId, Genre genre) {
        genreFilm.putIfAbsent(filmId, new HashSet<>());
        if (genreFilm.get(filmId).contains(genre)) {
            log.info("Film are already have this genre");
            return false;
        }
        return genreFilm.get(filmId).add(genre);
    }

    @Override
    public Boolean removeGenreFromFilm(Integer filmId, Genre genre) {
        if (genreFilm.get(filmId) == null || !genreFilm.get(filmId).contains(genre))
            throw new IllegalArgumentException("The film doesn't have the genre");
        return genreFilm.get(filmId).remove(genre);
    }
}
