package ru.artq.practice.kinopoisk.service;

import java.util.Collection;

public interface GenreFilmService {

    Collection<String> getAllGenreFilm(Integer filmId);

    Boolean addGenreToFilm(Integer filmId, String genre);

    Boolean removeGenreFromFilm(Integer filmId, String genre);
}
