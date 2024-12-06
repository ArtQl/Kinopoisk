package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.DirectorFilmService;
import ru.artq.practice.kinopoisk.service.FilmService;
import ru.artq.practice.kinopoisk.service.GenreFilmService;
import ru.artq.practice.kinopoisk.service.MpaFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.LikeStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final DirectorFilmService directorFilmService;
    private final GenreFilmService genreFilmService;
    private final MpaFilmService mpaFilmService;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;

    @Override
    public Film addFilm(Film film) {
        ValidationService.validateFilm(film);
        film = filmStorage.addFilm(film);
        genreFilmService.addGenresToFilm(film);
        mpaFilmService.addMpaToFilm(film);
        directorFilmService.addDirectorToFilm(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        ValidationService.validateFilm(film);
        filmStorage.updateFilm(film);
        genreFilmService.addGenresToFilm(film);
        mpaFilmService.addMpaToFilm(film);
        directorFilmService.addDirectorToFilm(film);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = filmStorage.getFilms();
        films.forEach(film -> film.setGenres(genreFilmService.getGenresFilm(film.getId())));
        films.forEach(film -> film.setMpa(mpaFilmService.getMpaFilm(film.getId())));
        films.forEach(film -> film.setDirectors(directorFilmService.getDirectorsFilm(film.getId())));
        return films;
    }

    @Override
    public Film getFilmById(Integer id) {
        Film film = filmStorage.getFilm(id);
        film.setGenres(genreFilmService.getGenresFilm(film.getId()));
        film.setMpa(mpaFilmService.getMpaFilm(film.getId()));
        film.setDirectors(directorFilmService.getDirectorsFilm(film.getId()));
        return film;
    }

    @Override
    public Collection<Film> search(String query) {
        if (query == null || query.isBlank())
            return Collections.emptyList();
        return filmStorage.findFilm(query);
    }

    @Override
    public Boolean likeFilm(Integer userId, Integer filmId) {
        userStorage.getUser(userId);
        filmStorage.getFilm(filmId);
        return likeStorage.likeFilm(userId, filmId);
    }

    @Override
    public Boolean unlikeFilm(Integer userId, Integer filmId) {
        userStorage.getUser(userId);
        filmStorage.getFilm(filmId);
        return likeStorage.unlikeFilm(userId, filmId);
    }

    @Override
    public Collection<Film> getPopularFilms(Integer count) {
        return likeStorage.getPopularFilms(count)
                .stream().map(filmStorage::getFilm).toList();
    }

    @Override
    public Collection<Integer> getFilmLikes(Integer filmId) {
        return likeStorage.getFilmLikes(filmStorage.getFilm(filmId).getId());
    }
}
