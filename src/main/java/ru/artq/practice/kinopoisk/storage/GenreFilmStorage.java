package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Set;

public interface GenreFilmStorage {

    void addGenresToFilm(Film film);

    Set<Integer> getGenresFilm(Integer filmId);
}
