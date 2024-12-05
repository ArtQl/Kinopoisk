package ru.artq.practice.kinopoisk.storage.inmemory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ReviewIdExistException;
import ru.artq.practice.kinopoisk.exception.ReviewNotExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;

import java.util.*;

@Component
@Profile("in-memory")
public class InMemoryReviewStorage implements ReviewStorage {
    private final Map<Integer, Review> reviews = new HashMap<>();
    private Integer id;

    private Integer setId() {
        if (reviews.isEmpty()) id = 0;
        return ++id;
    }

    @Override
    public Review addReview(Review review) {
        if (review.getReviewId() != null)
            throw new ReviewIdExistException("Id review not null");
        review.setReviewId(setId());
        reviews.put(review.getReviewId(), review);
        return review;
    }

    @Override
    public Review removeReview(Integer id) {
        if (!reviews.containsKey(id))
            throw new ReviewNotExistException("Review not found");
        return reviews.remove(id);
    }


    @Override
    public Review updateReview(Review review) {
        if (review.getReviewId() == null)
            throw new ReviewIdExistException("Review id is null");
        if (!reviews.containsKey(review.getReviewId()))
            throw new ReviewNotExistException("Review not exist with this id");
        reviews.put(review.getReviewId(), review);
        return review;
    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId, Integer count) {
        Collection<Review> res = reviews.values().stream()
                .filter(rev -> rev.getFilmId().equals(filmId)).limit(count).toList();
        if (res.isEmpty())
            throw new FilmNotExistException("Film not exist");
        return res;
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId, Integer count) {
        Collection<Review> res = reviews.values().stream()
                .filter(rev -> rev.getUserId().equals(userId)).limit(count).toList();
        if (res.isEmpty())
            throw new UserNotExistException("User not exist");
        return res;
    }

    @Override
    public Collection<Review> getAllReviews(Integer count) {
        return reviews.isEmpty() ? List.of() : reviews.values().stream().limit(count).toList();
    }

    @Override
    public Review getReviewById(Integer id) {
        return Optional.ofNullable(reviews.get(id))
                .orElseThrow(() -> new ReviewIdExistException("Review Id not exist"));
    }

    @Override
    public Boolean likeReview(Integer id, Integer userId) {
        if (!reviews.containsKey(id))
            throw new ReviewIdExistException("Review Id not exist");
        return reviews.get(id).likeReview(userId);
    }

    @Override
    public Boolean dislikeReview(Integer id, Integer userId) {
        if (!reviews.containsKey(id))
            throw new ReviewIdExistException("Review Id not exist");
        return reviews.get(id).dislikeReview(userId);
    }

    @Override
    public Boolean removeLikeReview(Integer id, Integer userId) {
        if (!reviews.containsKey(id))
            throw new ReviewIdExistException("Review Id not exist");
        return reviews.get(id).removeLike(userId);
    }

    @Override
    public Boolean removeDislikeReview(Integer id, Integer userId) {
        if (!reviews.containsKey(id))
            throw new ReviewIdExistException("Review Id not exist");
        return reviews.get(id).removeDislike(userId);
    }
}
