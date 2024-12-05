package ru.artq.practice.kinopoisk.service.impl;

import ru.artq.practice.kinopoisk.service.AdviceService;
import ru.artq.practice.kinopoisk.storage.AdviceStorage;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.UserStorage;

public class AdviceServiceImpl implements AdviceService {
    UserStorage userStorage;
    FilmStorage filmStorage;
    AdviceStorage adviceStorage;
    @Override
    public void adviceFilm(Integer userId, Integer friendId, Integer filmId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
        filmStorage.getFilm(filmId);
        adviceStorage.adviceFilm(userId, friendId, filmId);
    }
}
