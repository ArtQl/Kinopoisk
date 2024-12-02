package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.service.ReviewService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Getter
public class ReviewServiceImpl implements ReviewService {
    UserStorage userStorage;
    FilmStorage filmStorage;
    ReviewStorage reviewStorage;

    @Override
    public void addReviewOfFilm(Integer filmId, Integer userId, String review) {
        userStorage.getUser(userId);
        filmStorage.getFilm(filmId);
        reviewStorage.addReviewOfFilm(filmId, userId, review);
    }

    @Override
    public void removeReviewOfFilm(Integer filmId, Integer userId) {
        userStorage.getUser(userId);
        filmStorage.getFilm(filmId);
        reviewStorage.removeReviewOfFilm(filmId, userId);
    }

    @Override
    public void updateReviewOfFilm(Integer filmId, Integer userId, String review) {
        userStorage.getUser(userId);
        filmStorage.getFilm(filmId);
        reviewStorage.updateReviewOfFilm(filmId, userId, review);
    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId) {
        filmStorage.getFilm(filmId);
        return reviewStorage.getAllReviewsOfFilm(filmId);
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId) {
        userStorage.getUser(userId);
        return reviewStorage.getAllReviewsOfUser(userId);
    }
}
