package ru.artq.practice.kinopoisk.storage;

import java.util.Collection;

public interface LikeStorage {
    Boolean likeFilm(Integer userId, Integer filmId);

    Boolean unlikeFilm(Integer userId, Integer filmId);

    Collection<Integer> getPopularFilms(Integer count);

    Collection<Integer> getUserLikes(Integer userId);
}
