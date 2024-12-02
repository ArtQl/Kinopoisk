package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.service.GenreFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreController {
    private final GenreFilmService genreFilmService;

    @GetMapping("/{id}/genres")
    public Collection<String> getAllGenreFilm(@PathVariable Integer id) {
        return genreFilmService.getAllGenreFilm(id);
    }

    @PostMapping("/{id}/genres/{genre}")
    public Boolean addGenreToFilm(@PathVariable Integer id, @PathVariable String genre) {
        return genreFilmService.addGenreToFilm(id, genre);
    }

    @DeleteMapping("/{id}/genres/{genre}")
    public Boolean removeGenreFromFilm(@PathVariable Integer id, @PathVariable String genre) {
        return genreFilmService.removeGenreFromFilm(id, genre);
    }
}
