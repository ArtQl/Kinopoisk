package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface CategoryStorage {
    Collection<Film> getTopFilmByYear(Integer year);

    Collection<Film> getTopFilmByGenre(String genre);

    Collection<Film> getFilmsDirector(Integer directorId, String sortBy);
}
