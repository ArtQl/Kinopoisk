package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.service.ReviewService;

import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewController {
    ReviewService reviewService;

    @PostMapping("/films/{filmId}/reviews/{userId}")
    public Boolean addReviewOfFilm(@PathVariable Integer filmId, @PathVariable Integer userId, @RequestBody String review) {
        reviewService.addReviewOfFilm(filmId, userId, review);
        return true;
    }

    @DeleteMapping("/films/{filmId}/reviews/{userId}")
    public Boolean removeReviewOfFilm(@PathVariable Integer filmId, @PathVariable Integer userId) {
        reviewService.removeReviewOfFilm(filmId, userId);
        return true;
    }

    @PutMapping("/films/{filmId}/reviews/{userId}")
    public Boolean updateReviewOfFilm(@PathVariable Integer filmId, @PathVariable Integer userId, @RequestBody String review) {
        reviewService.updateReviewOfFilm(filmId, userId, review);
        return true;
    }

    @GetMapping("/films/{filmId}/reviews")
    public Collection<Review> getAllReviewsOfFilm(@PathVariable Integer filmId) {
        return reviewService.getAllReviewsOfFilm(filmId);
    }

    @GetMapping("/users/{userId}/reviews")
    Collection<Review> getAllReviewsOfUser(@PathVariable Integer userId) {
        return reviewService.getAllReviewsOfUser(userId);
    }
}
