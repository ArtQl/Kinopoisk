package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface FilmService {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilmById(Integer id);
}
