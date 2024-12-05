package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.LikeFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class LikeController {
    private final LikeFilmService likeFilmService;

    @PostMapping("/likes")
    public Boolean likeFilm(@RequestParam Integer id,
                            @RequestParam Integer userId) {
        return likeFilmService.likeFilm(userId, id);
    }

    @DeleteMapping("/likes")
    public Boolean unlikeFilm(@RequestParam Integer id,
                              @RequestParam Integer userId) {
        return likeFilmService.unlikeFilm(userId, id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        return likeFilmService.getPopularFilms(count);
    }

    @GetMapping("/likes")
    public Collection<Integer> getUserLikes(@RequestParam Integer userId) {
        return likeFilmService.getUserLikes(userId);
    }

    @GetMapping("/likes")
    public Collection<Integer> getFilmLikes(@RequestParam Integer filmId) {
        return likeFilmService.getFilmLikes(filmId);
    }

    @GetMapping("/common")
    public Collection<Film> getCommonFilms(@RequestParam Integer userId,
                                           @RequestParam Integer friendId) {
        return likeFilmService.getCommonFilms(userId, friendId);
    }
}
