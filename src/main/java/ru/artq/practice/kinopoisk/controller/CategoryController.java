package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.CategoryService;

import java.util.Collection;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    CategoryService categoryService;

    @GetMapping("/genre/{genre}")
    public Collection<Film> getTopFilmByGenre(@PathVariable String genre) {
        return categoryService.getTopFilmByGenre(genre);
    }

    @GetMapping("/year/{year}")
    public Collection<Film> getTopFilmByYear(@PathVariable Integer year) {
        return categoryService.getTopFilmByYear(year);
    }

    @GetMapping("/director/{id}")
    public Collection<Film> getFilmsDirector(
            @PathVariable Integer id,
            @RequestParam String sortBy) {
        return categoryService.getFilmsDirector(id, sortBy);
    }
}
