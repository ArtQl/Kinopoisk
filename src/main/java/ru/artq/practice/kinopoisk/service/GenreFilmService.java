package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Set;

public interface GenreFilmService {

    void addGenresToFilm(Film film);

    Set<Integer> getGenresFilm(Integer filmId);
}
