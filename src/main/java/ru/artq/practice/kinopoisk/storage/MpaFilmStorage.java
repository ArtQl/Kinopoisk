package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Set;

public interface MpaFilmStorage {

    void addMpaToFilm(Film film);

    Set<Integer> getMpaFilm(Integer filmId);
}
