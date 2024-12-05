package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Review;

import java.util.Collection;

public interface ReviewService {
    Review addReview(Review review);

    Review removeReview(Integer id);

    Review updateReview(Review review);

    Collection<Review> getReviewsOfFilm(Integer filmId, Integer count);

    Collection<Review> getReviewsOfUser(Integer userId, Integer count);

    Review getReviewById(Integer id);

    Boolean likeReview(Integer id, Integer userId);

    Boolean dislikeReview(Integer id, Integer userId);

    Boolean removeLikeReview(Integer id, Integer userId);

    Boolean removeDislikeReview(Integer id, Integer userId);
}
