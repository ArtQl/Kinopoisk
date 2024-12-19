package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface CategoryService {
    Collection<Film> getTopFilmByGenre(String genre);

    Collection<Film> getTopFilmByYear(Integer year);

    Collection<Film> getFilmsDirector(Integer id, String sortBy);
}
