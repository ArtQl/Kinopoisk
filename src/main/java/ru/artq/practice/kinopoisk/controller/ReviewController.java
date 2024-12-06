package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.service.ReviewService;

import java.util.Collection;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    ReviewService reviewService;

    @PostMapping
    public Review addReviewOfFilm(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @DeleteMapping("/{id}")
    public Review removeReviewOfFilm(@PathVariable Integer id) {
        return reviewService.removeReview(id);
    }

    @PutMapping
    public Review updateReviewOfFilm(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Integer id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/film")
    public Collection<Review> getReviewsOfFilm(
            @RequestParam(defaultValue = "0") Integer filmId,
            @RequestParam(defaultValue = "10") Integer count) {
        return reviewService.getReviewsOfFilm(filmId, count);
    }

    @GetMapping("/user")
    public Collection<Review> getReviewsOfUser(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "10") Integer count) {
        return reviewService.getReviewsOfUser(userId, count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Boolean likeReview(@PathVariable Integer id,
                              @PathVariable Integer userId) {
        return reviewService.likeReview(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public Boolean dislikeReview(@PathVariable Integer id,
                                 @PathVariable Integer userId) {
        return reviewService.dislikeReview(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Boolean removeLikeReview(@PathVariable Integer id,
                                    @PathVariable Integer userId) {
        return reviewService.removeLikeReview(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public Boolean removeDislikeReview(@PathVariable Integer id,
                                       @PathVariable Integer userId) {
        return reviewService.removeDislikeReview(id, userId);
    }
}