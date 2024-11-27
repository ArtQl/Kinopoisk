package ru.artq.practice.kinopoisk.storage.impl.indb;

import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.inter.FilmStorage;

import java.util.Collection;
import java.util.List;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return List.of();
    }

    @Override
    public Film getFilm(Integer id) {
        return null;
    }
}
