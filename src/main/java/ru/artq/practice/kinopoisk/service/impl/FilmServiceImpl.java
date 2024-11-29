package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.Collection;

@Slf4j
@Getter
@Service
public class FilmServiceImpl implements FilmService {
    FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Override
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public Film getFilmById(Integer id) {
        return filmStorage.getFilm(id);
    }
}
