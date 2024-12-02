package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.service.LikeFilmService;
import ru.artq.practice.kinopoisk.service.MPAFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmController {
    private final FilmService filmService;
    private final LikeFilmService likeFilmService;
    private final GenreFilmService genreFilmService;
    private final MPAFilmService mpaFilmService;

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

    @GetMapping("/genres/{id}")
    public Collection<String> getAllGenreFilm(@PathVariable Integer id) {
        return genreFilmService.getAllGenreFilm(id);
    }

    @PutMapping("/genres/{id}/{genre}")
    public Boolean addGenreToFilm(@PathVariable Integer id, @PathVariable String genre) {
        return genreFilmService.addGenreToFilm(id, genre);
    }

    @DeleteMapping("/genres/{id}/{genre}")
    public Boolean removeGenreFromFilm(@PathVariable Integer id, @PathVariable String genre){
        return genreFilmService.removeGenreFromFilm(id, genre);
    }

    @GetMapping("/mpa/{id}")
    public Collection<String> getAllMpaFilm(@PathVariable Integer id) {
        return mpaFilmService.getAllMPAFilm(id);
    }

    @PutMapping("/mpa/{id}/{type}")
    public Boolean addMpaToFilm(@PathVariable Integer id, @PathVariable String type) {
        return mpaFilmService.addMPAToFilm(id, type);
    }

    @DeleteMapping("/mpa/{id}/{type}")
    public Boolean removeMpaFromFilm(@PathVariable Integer id, @PathVariable String type){
        return mpaFilmService.removeMPAFromFilm(id, type);
    }
}
