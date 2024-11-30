package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface LikeFilmService {
    Boolean likeFilm(Integer userId, Integer filmId);

    Boolean unlikeFilm(Integer userId, Integer filmId);

    Collection<Film> getPopularFilms(Integer count);

    Collection<Integer> getUserLikes(Integer userId);

    Collection<Integer> getFilmLikes(Integer filmId);

}
