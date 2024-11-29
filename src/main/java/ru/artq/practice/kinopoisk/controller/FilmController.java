package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.impl.FilmServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmServiceImpl filmServiceImpl;

    @Autowired
    public FilmController(FilmServiceImpl filmServiceImpl) {
        this.filmServiceImpl = filmServiceImpl;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmServiceImpl.getFilmStorage().addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmServiceImpl.getFilmStorage().updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmServiceImpl.getFilmStorage().getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return filmServiceImpl.getFilmStorage().getFilm(id);
    }

//    @PutMapping("/{id}/like/{userId}")
//    public boolean likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
//        return filmServiceImpl.likeFilm(id, userId);
//    }
//
//    @DeleteMapping("/{id}/like/{userId}")
//    public boolean unlikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
//        return filmServiceImpl.unlikeFilm(id, userId);
//    }
//
//    @GetMapping("/popular")
//    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
//        return filmServiceImpl.getPopularFilms(count);
//    }
}
