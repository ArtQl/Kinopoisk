package ru.artq.practice.kinopoisk.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.user.FilmException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.film.FilmStorage;
import ru.artq.practice.kinopoisk.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public boolean likeFilm(Integer filmId, Integer userId) {
        userStorage.getUser(userId);
        Film film = filmStorage.getFilm(filmId);
        if(!film.getLikes().add(userId))
            throw new FilmException("User has already rated the movie");
        return true;
    }

    public boolean unlikeFilm(Integer filmId, Integer userId) {
        userStorage.getUser(userId);
        Film film = filmStorage.getFilm(filmId);
        if(!film.getLikes().remove(userId))
            throw new FilmException("User have yet to rate the movie");
        return true;
    }

    public List<Film> getPopularFilms(Integer count) {
        if (count <= 0)
            throw new ValidationException("Count <= 0");
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(f -> f.getLikes().size()))
                .limit(count).toList();
    }
}
