package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Review;

import java.util.Collection;

public interface ReviewStorage {
    Review addReview(Review review);

    Review removeReview(Integer id);

    Review updateReview(Review review);

    Collection<Review> getAllReviewsOfFilm(Integer filmId, Integer count);

    Collection<Review> getAllReviewsOfUser(Integer userId, Integer count);

    Collection<Review> getAllReviews(Integer count);

    Review getReviewById(Integer id);

    Boolean likeReview(Integer id, Integer userId);

    Boolean removeLikeReview(Integer id, Integer userId);

    Boolean dislikeReview(Integer id, Integer userId);

    Boolean removeDislikeReview(Integer id, Integer userId);
}
