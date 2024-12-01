package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Getter
public class GenreFilmServiceImpl implements GenreFilmService {
    private final GenreFilmStorage genreFilmStorage;
    private final FilmStorage filmStorage;

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
