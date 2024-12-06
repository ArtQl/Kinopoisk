package ru.artq.practice.kinopoisk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.CategoryService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    FilmStorage filmStorage;

    @Override
    public Collection<Film> getTopFilmByGenre(String genre) {
        return filmStorage.getTopFilmByGenre(genre);
    }

    @Override
    public Collection<Film> getTopFilmByYear(Integer year) {
        return filmStorage.getTopFilmByYear(year);
    }

    @Override
    public Collection<Film> getFilmsDirector(Integer directorId, String sortBy) {
//        if (!sortBy.equals("year") || !sortBy.equals("likes")) sortBy = "year";
        return filmStorage.getFilmsDirector(directorId, sortBy);
    }
}
