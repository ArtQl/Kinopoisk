package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Director;

import java.util.Collection;

public interface DirectorStorage {

    Director addDirector(Director director);

    Director updateDirector(Director director);

    Boolean deleteDirector(Integer directorId);

    Collection<Director> getDirectors();

    Director getDirector(Integer directorId);

    void addDirectorToFilm(Integer filmId, Integer directorId);

    void updateDirectorToFilm(Integer filmId, Integer directorId);

    void deleteDirectorToFilm(Integer filmId, Integer directorId);

    Collection<Integer> getFilmsOfDirector(Integer directorId);
}
