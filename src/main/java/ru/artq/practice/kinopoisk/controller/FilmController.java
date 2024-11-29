package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;
import ru.artq.practice.kinopoisk.service.LikeFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final LikeFilmService likeFilmService;

    @Autowired
    public FilmController(FilmService filmService, LikeFilmService likeFilmService) {
        this.filmService = filmService;
        this.likeFilmService = likeFilmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Boolean likeFilm(@PathVariable Integer filmId, @PathVariable Integer userId) {
        return likeFilmService.likeFilm(userId, filmId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Boolean unlikeFilm(@PathVariable Integer filmId, @PathVariable Integer userId) {
        return likeFilmService.unlikeFilm(userId, filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return likeFilmService.getPopularFilms(count);
    }



}
