package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;
import java.util.Set;

public interface DirectorStorage {

    void addDirectorToFilm(Film film);

    Collection<Integer> getFilmsDirector(Integer directorId);

    Set<Integer> getDirectorsFilm(Integer filmId);
}
