package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.model.enums.EventType;
import ru.artq.practice.kinopoisk.model.enums.Operation;
import ru.artq.practice.kinopoisk.service.ReviewService;
import ru.artq.practice.kinopoisk.storage.EventStorage;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Getter
public class ReviewServiceImpl implements ReviewService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final ReviewStorage reviewStorage;
    private final EventStorage eventStorage;

    @Override
    public Review addReview(Review review) {
        userStorage.getUser(review.getUserId());
        filmStorage.getFilm(review.getFilmId());
        review = reviewStorage.addReview(review);
        eventStorage.addEventToFeed(
                review.getUserId(), review.getReviewId(),
                EventType.REVIEW, Operation.ADD);
        return review;
    }

    @Override
    public Review removeReview(Integer id) {
        Review review = reviewStorage.removeReview(id);
        eventStorage.addEventToFeed(
                review.getUserId(), review.getReviewId(),
                EventType.REVIEW, Operation.REMOVE);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        userStorage.getUser(review.getUserId());
        filmStorage.getFilm(review.getFilmId());
        review = reviewStorage.updateReview(review);
        eventStorage.addEventToFeed(
                review.getUserId(), review.getReviewId(),
                EventType.REVIEW, Operation.UPDATE);
        return review;
    }

    @Override
    public Collection<Review> getReviewsOfFilm(Integer filmId, Integer count) {
        if (filmId == 0) return reviewStorage.getAllReviews(count);
        filmStorage.getFilm(filmId);
        return reviewStorage.getAllReviewsOfFilm(filmId, count);
    }

    @Override
    public Collection<Review> getReviewsOfUser(Integer userId, Integer count) {
        if (userId == 0) return reviewStorage.getAllReviews(count);
        userStorage.getUser(userId);
        return reviewStorage.getAllReviewsOfUser(userId, count);
    }

    @Override
    public Review getReviewById(Integer id) {
        return reviewStorage.getReviewById(id);
    }

    @Override
    public Boolean likeReview(Integer id, Integer userId) {
        userStorage.getUser(userId);
        return reviewStorage.likeReview(id, userId);
    }

    @Override
    public Boolean dislikeReview(Integer id, Integer userId) {
        userStorage.getUser(userId);
        return reviewStorage.dislikeReview(id, userId);
    }

    @Override
    public Boolean removeLikeReview(Integer id, Integer userId) {
        userStorage.getUser(userId);
        return reviewStorage.removeLikeReview(id, userId);
    }

    @Override
    public Boolean removeDislikeReview(Integer id, Integer userId) {
        userStorage.getUser(userId);
        return reviewStorage.removeDislikeReview(id, userId);
    }
}