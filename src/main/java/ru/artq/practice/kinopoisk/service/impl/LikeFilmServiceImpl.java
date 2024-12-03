package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.LikeFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.LikeStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class LikeFilmServiceImpl implements LikeFilmService {
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

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
    public Collection<Integer> getUserLikes(Integer userId) {
        userStorage.getUser(userId);
        return likeStorage.getUserLikes(userId);
    }

    @Override
    public Collection<Integer> getFilmLikes(Integer filmId) {
        filmStorage.getFilm(filmId);
        return likeStorage.getFilmLikes(filmId);
    }

    @Override
    public Collection<Film> getMutualFilms(Integer userId, Integer otherUserId) {
        userStorage.getUser(userId);
        userStorage.getUser(otherUserId);
        Collection<Integer> res = likeStorage.getMutualFilms(otherUserId, userId);
        return res.isEmpty() ? List.of() : res.stream().map(filmStorage::getFilm).toList();
    }
}