package ru.artq.practice.kinopoisk.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Review;

import java.util.Collection;
import java.util.List;

@Component
@Profile("db")
public class InDbReviewStorage implements ReviewStorage {
    @Override
    public void addReviewOfFilm(Integer filmId, Integer userId, String review) {

    }

    @Override
    public void removeReviewOfFilm(Integer filmId, Integer userId) {

    }

    @Override
    public void updateReviewOfFilm(Integer filmId, Integer userId, String review) {

    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId) {
        return List.of();
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId) {
        return List.of();
    }
}
