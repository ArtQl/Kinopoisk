package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.CategoryService;

import java.util.Collection;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
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
}
