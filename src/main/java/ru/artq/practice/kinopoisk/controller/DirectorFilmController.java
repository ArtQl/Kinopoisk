package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.DirectorFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DirectorFilmController {
    DirectorFilmService directorFilmService;

    @PostMapping("/directors")
    public Director addDirector(@RequestBody Director director) {
        return directorFilmService.addDirector(director);
    }

    @PutMapping("/directors")
    public Director updateDirector(@RequestBody Director director) {
        return directorFilmService.updateDirector(director);
    }

    @DeleteMapping("/directors")
    public Boolean deleteDirector(Integer directorId) {
        return directorFilmService.deleteDirector(directorId);
    }

    @GetMapping("/directors")
    public Collection<Director> getDirectors() {
        return directorFilmService.getDirectors();
    }

    @GetMapping("/directors/{id}")
    public Director getDirectorById(@PathVariable Integer id) {
        return directorFilmService.getDirector(id);
    }

    @PostMapping("/{filmId}/directors/{directorId}")
    public Boolean addDirectorToFilm(@PathVariable Integer filmId, @PathVariable Integer directorId) {
        directorFilmService.addDirectorToFilm(filmId, directorId);
        return true;
    }

    @PutMapping("/{filmId}/directors/{directorId}")
    public Boolean updateDirectorToFilm(@PathVariable Integer filmId, @PathVariable Integer directorId) {
        directorFilmService.updateDirectorToFilm(filmId, directorId);
        return true;
    }

    @DeleteMapping("/{filmId}/directors/{directorId}")
    public Boolean deleteDirectorToFilm(@PathVariable Integer filmId, @PathVariable Integer directorId) {
        directorFilmService.deleteDirectorToFilm(filmId, directorId);
        return true;
    }

    @GetMapping("/directors/{directorId}/films")
    public Collection<Film> getFilmsOfDirector(@PathVariable Integer directorId) {
        return directorFilmService.getFilmsOfDirector(directorId);
    }
}