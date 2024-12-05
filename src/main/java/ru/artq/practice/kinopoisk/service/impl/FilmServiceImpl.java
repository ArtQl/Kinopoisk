package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Override
    public Film addFilm(Film film) {
        ValidationService.validateFilm(film);
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        ValidationService.validateFilm(film);
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

    @Override
    public Collection<Film> findFilm(String query) {
        if (query == null || query.isBlank())
            return Collections.emptyList();
        return filmStorage.findFilm(query);
    }
}
