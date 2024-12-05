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

    @GetMapping("/mutual")
    public Collection<Film> getMutualFilms(@RequestParam Integer userId,
                                           @RequestParam Integer otherUserId) {
        return likeFilmService.getMutualFilms(userId, otherUserId);
    }
}
