package ru.artq.practice.kinopoisk.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
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

    @PostMapping("/{id}/likes/{userId}")
    public Boolean likeFilm(@PathVariable Integer id,
                            @PathVariable Integer userId) {
        return filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/likes/{userId}")
    public Boolean unlikeFilm(@PathVariable Integer id,
                              @PathVariable Integer userId) {
        return filmService.unlikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }

    @GetMapping("/{id}/likes")
    public Collection<Integer> getFilmLikes(@PathVariable Integer id) {
        return filmService.getFilmLikes(id);
    }

    @GetMapping("/search")
    public Collection<Film> search(@RequestParam @NotBlank String query) {
        return filmService.search(query);
    }
}