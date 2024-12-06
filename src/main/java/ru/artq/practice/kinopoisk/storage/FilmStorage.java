package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(Integer id);

    Collection<Film> getTopFilmByYear(Integer year);

    Collection<Film> getTopFilmByGenre(String genre);

    Collection<Film> getFilmsDirector(Integer directorId, String sortBy);

    Collection<Film> findFilm(String query);

    void clear();
}
