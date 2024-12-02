package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;
import ru.artq.practice.kinopoisk.util.Validation;

import java.util.Collection;

@Service
@Getter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
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
        return genreFilmStorage.addGenreToFilm(filmId, Validation.validateGenre(genre));
    }

    @Override
    public Boolean removeGenreFromFilm(Integer filmId, String genre) {
        filmStorage.getFilm(filmId);
        return genreFilmStorage.removeGenreFromFilm(filmId, Validation.validateGenre(genre));
    }
}
