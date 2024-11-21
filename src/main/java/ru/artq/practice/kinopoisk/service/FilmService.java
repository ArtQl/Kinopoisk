package ru.artq.practice.kinopoisk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.film.FilmStorage;

import java.util.List;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Integer idFilm) {

    }

    public boolean removeLike(Integer idFilm) {
        return false;
    }

    public List<Film> getPopularFilms() {
        return List.of();
        // return 10 popular films by likes
    }
}
