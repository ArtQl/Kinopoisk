package ru.artq.practice.kinopoisk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.CategoryService;
import ru.artq.practice.kinopoisk.storage.CategoryStorage;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryStorage categoryStorage;

    @Override
    public Collection<Film> getTopFilmByGenre(String genre) {
        return categoryStorage.getTopFilmByGenre(genre);
    }

    @Override
    public Collection<Film> getTopFilmByYear(Integer year) {
        return categoryStorage.getTopFilmByYear(year);
    }

    @Override
    public Collection<Film> getFilmsDirector(Integer directorId, String sortBy) {
        return categoryStorage.getFilmsDirector(directorId, sortBy);
    }
}
