package ru.artq.practice.kinopoisk.service;

import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;

import java.util.Collection;

public interface DirectorFilmService {

    Director addDirector(Director director);

    Director updateDirector(Director director);

    Boolean deleteDirector(Integer directorId);

    Collection<Director> getDirectors();

    Director getDirector(Integer directorId);

    void addDirectorToFilm(Integer filmId, Integer directorId);

    void updateDirectorToFilm(Integer filmId, Integer directorId);

    void deleteDirectorToFilm(Integer filmId, Integer directorId);

    Collection<Film> getFilmsOfDirector(Integer directorId);
}
