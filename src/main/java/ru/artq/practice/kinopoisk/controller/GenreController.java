package ru.artq.practice.kinopoisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.AbstractService;
import ru.artq.practice.kinopoisk.service.GenreFilmService;

import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController extends AbstractController<Genre> {
    GenreFilmService genreFilmService;
    @Autowired
    public GenreController(AbstractService<Genre> abstractService,
                           GenreFilmService genreFilmService) {
        super(abstractService);
        this.genreFilmService = genreFilmService;
    }

    @PostMapping("/film")
    public void addGenresToFilm(Film film) {
        genreFilmService.addGenresToFilm(film);
    }

    @GetMapping("/film")
    public Set<Integer> getGenresFilm(Integer filmId) {
        return genreFilmService.getGenresFilm(filmId);
    }

}
