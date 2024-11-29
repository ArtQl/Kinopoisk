package ru.artq.practice.kinopoisk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.Collection;

@Service
public class GenreFilmServiceImpl implements GenreFilmService {
    GenreFilmStorage genreFilmStorage;
    FilmStorage filmStorage;

    @Autowired
    public GenreFilmServiceImpl(
            @Qualifier("inDbGenreFilmStorage") GenreFilmStorage genreFilmStorage,
            @Qualifier("inDbFilmStorage") FilmStorage filmStorage) {
        this.genreFilmStorage = genreFilmStorage;
        this.filmStorage = filmStorage;
    }

    @Override
    public Collection<String> getAllGenreFilm(Integer filmId) {
        filmStorage.getFilm(filmId);
        return genreFilmStorage.getAllGenreFilm(filmId).stream().map(Genre::name).toList();
    }

    @Override
    public Boolean addGenreToFilm(Integer filmId, String genre) {
        filmStorage.getFilm(filmId);
        return genreFilmStorage.addGenreToFilm(filmId, validateGenre(genre));
    }

    @Override
    public Boolean removeGenreFromFilm(Integer filmId, String genre) {
        filmStorage.getFilm(filmId);
        return genreFilmStorage.removeGenreFromFilm(filmId, validateGenre(genre));
    }

    private Genre validateGenre(String genre) {
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
}
