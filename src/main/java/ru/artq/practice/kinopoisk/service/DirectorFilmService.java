package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;
import java.util.Set;

public interface DirectorFilmService {

    void addDirectorToFilm(Film film);

    Set<Integer> getDirectorsFilm(Integer filmId);

}
