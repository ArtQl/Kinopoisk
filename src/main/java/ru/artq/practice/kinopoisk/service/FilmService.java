package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface FilmService {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilmById(Integer id);

    Collection<Film> search(String query);

    Boolean likeFilm(Integer filmId, Integer userId);

    Boolean unlikeFilm(Integer filmId, Integer userId);

    Collection<Film> getPopularFilms(Integer count);

    Collection<Integer> getFilmLikes(Integer filmId);

}
