package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Genre;

import java.util.Collection;

public interface GenreFilmStorage {

    Collection<Genre> getAllGenreFilm(Integer filmId);

    Boolean addGenreToFilm(Integer filmId, Genre genre);

    Boolean removeGenreFromFilm(Integer filmId, Genre genre);

    Collection<Integer> getTopFilmByGenre(Genre genre);
}
