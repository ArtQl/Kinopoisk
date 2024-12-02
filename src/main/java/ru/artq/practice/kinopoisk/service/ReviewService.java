package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Review;

import java.util.Collection;

public interface ReviewService {
    void addReviewOfFilm(Integer filmId, Integer userId, String review);

    void removeReviewOfFilm(Integer filmId, Integer userId);

    void updateReviewOfFilm(Integer filmId, Integer userId, String review);

    Collection<Review> getAllReviewsOfFilm(Integer filmId);

    Collection<Review> getAllReviewsOfUser(Integer userId);
}
