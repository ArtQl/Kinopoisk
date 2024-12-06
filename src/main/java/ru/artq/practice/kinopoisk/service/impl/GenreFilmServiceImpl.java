package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.AbstractService;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.Collection;
import java.util.Set;

@Service
@Getter
public class GenreFilmServiceImpl extends AbstractService<Genre> implements GenreFilmService {
    private final GenreFilmStorage genreStorage;
    @Autowired
    public GenreFilmServiceImpl(AbstractEntityStorage<Genre> storage, GenreFilmStorage genreStorage) {
        super(storage);
        this.genreStorage = genreStorage;
    }

    @Override
    public void addGenresToFilm(Film film) {
        if (film.getGenres().isEmpty()) return;
        genreStorage.addGenresToFilm(film);
    }

    @Override
    public Set<Integer> getGenresFilm(Integer filmId) {
        return genreStorage.getGenresFilm(filmId);
    }
}
