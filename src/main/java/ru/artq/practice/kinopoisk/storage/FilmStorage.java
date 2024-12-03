package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(Integer id);

    Collection<Film> getTopFilmByYear(Integer year);

    Collection<Film> findFilm(String query);
}
