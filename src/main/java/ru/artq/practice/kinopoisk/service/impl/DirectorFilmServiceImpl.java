package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.DirectorFilmService;
import ru.artq.practice.kinopoisk.storage.DirectorStorage;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Getter
public class DirectorFilmServiceImpl implements DirectorFilmService {
    FilmStorage filmStorage;
    DirectorStorage directorStorage;

    @Override
    public Director addDirector(Director director) {
        return directorStorage.addDirector(director);
    }

    @Override
    public Director updateDirector(Director director) {
        return directorStorage.updateDirector(director);
    }

    @Override
    public Boolean deleteDirector(Integer directorId) {
        return directorStorage.deleteDirector(directorId);
    }

    @Override
    public Collection<Director> getDirectors() {
        return directorStorage.getDirectors();
    }

    @Override
    public Director getDirector(Integer directorId) {
        return directorStorage.getDirector(directorId);
    }

    @Override
    public void addDirectorToFilm(Integer filmId, Integer directorId) {
        filmStorage.getFilm(filmId);
        directorStorage.addDirectorToFilm(filmId, directorId);
    }

    @Override
    public void updateDirectorToFilm(Integer filmId, Integer directorId) {
        filmStorage.getFilm(filmId);
        directorStorage.updateDirectorToFilm(filmId, directorId);
    }

    @Override
    public void deleteDirectorToFilm(Integer filmId, Integer directorId) {
        filmStorage.getFilm(filmId);
        directorStorage.deleteDirectorToFilm(filmId, directorId);
    }

    @Override
    public Collection<Film> getFilmsOfDirector(Integer directorId) {
        return directorStorage.getFilmsOfDirector(directorId).stream().map(filmStorage::getFilm).toList();
    }
}
