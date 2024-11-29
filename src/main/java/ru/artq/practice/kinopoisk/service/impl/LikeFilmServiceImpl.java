package ru.artq.practice.kinopoisk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.LikeFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.LikeStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;

@Service
public class LikeFilmServiceImpl implements LikeFilmService {
    LikeStorage likeStorage;
    UserStorage userStorage;
    FilmStorage filmStorage;

    @Autowired
    public LikeFilmServiceImpl(@Qualifier("inDbLikeStorage") LikeStorage likeStorage,
                               @Qualifier("inDbUserStorage") UserStorage userStorage,
                               @Qualifier("inDbFilmStorage") FilmStorage filmStorage) {
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
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
        if (filmStorage.getFilms() == null)
            throw new FilmNotExistException("Films not exist");
        if (count == null || count <= 0 || count > filmStorage.getFilms().size())
            count = 10;
        return likeStorage.getPopularFilms(count)
                .stream().map(filmStorage::getFilm).toList();
    }

    @Override
    public Collection<Integer> getUserLikes(Integer userId) {
        userStorage.getUser(userId);
        return likeStorage.getUserLikes(userId);
    }
}