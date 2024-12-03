package ru.artq.practice.kinopoisk.storage.inmemory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ReviewNotExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;

import java.util.*;

@Component
@Profile("in-memory")
public class InMemoryReviewStorage implements ReviewStorage {
    private final Map<Integer, Set<Review>> reviews = new HashMap<>();
    private Integer id = 0;

    private Integer setId() {
        if (reviews.isEmpty()) id = 0;
        return ++id;
    }

    @Override
    public void addReviewOfFilm(Integer filmId, Integer userId, String review) {
        reviews.putIfAbsent(filmId, new HashSet<>());
        Review rev = new Review(setId(), filmId, userId, review);
        reviews.get(filmId).add(rev);
    }

    @Override
    public void removeReviewOfFilm(Integer filmId, Integer userId) {
        Set<Review> filmReviews = reviews.get(filmId);
        if (filmReviews == null)
            throw new ReviewNotExistException("Review not found");
        filmReviews.removeIf(review -> review.getUserId().equals(userId));
        if (filmReviews.isEmpty()) reviews.remove(filmId);
    }

    @Override
    public void updateReviewOfFilm(Integer filmId, Integer userId, String review) {
        if (reviews.get(filmId) == null)
            throw new ReviewNotExistException("Review not found");
        reviews.get(filmId).removeIf(rev -> rev.getUserId().equals(userId));
        reviews.get(filmId).add(new Review(setId(), filmId, userId, review));
    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId) {
        if (!reviews.containsKey(filmId))
            throw new FilmNotExistException("Film not exist");
        return reviews.get(filmId);
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId) {
        if(reviews.isEmpty()) return List.of();
        return reviews.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(review -> review.getUserId().equals(userId))
                .toList();
    }
}
