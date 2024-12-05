package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.LikeFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeController {
    private final LikeFilmService likeFilmService;

    @PostMapping("/{id}/like/{userId}")
    public Boolean likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        return likeFilmService.likeFilm(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Boolean unlikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        return likeFilmService.unlikeFilm(userId, id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return likeFilmService.getPopularFilms(count);
    }

    @GetMapping
    public Collection<Integer> getUserLikes(Integer userId) {
        return likeFilmService.getUserLikes(userId);
    }

    @GetMapping
    public Collection<Integer> getFilmLikes(Integer filmId) {
        return likeFilmService.getFilmLikes(filmId);
    }

    @GetMapping("/common")
    public Collection<Film> getCommonFilms(@RequestParam Integer userId,
                                           @RequestParam Integer friendId) {
        return likeFilmService.getCommonFilms(userId, friendId);
    }
}
