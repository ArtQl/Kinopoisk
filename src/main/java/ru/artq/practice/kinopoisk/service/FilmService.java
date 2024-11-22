package ru.artq.practice.kinopoisk.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.film.FilmStorage;
import ru.artq.practice.kinopoisk.storage.user.UserStorage;

import java.util.HashSet;
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
        if (film.getLikes() == null)
            film.setLikes(new HashSet<>());
        if (film.getLikes().contains(userId))
            throw new FilmException("User has already rated the movie");
        else
            film.getLikes().add(userId);
        return true;
    }

    public boolean unlikeFilm(Integer filmId, Integer userId) {
        userStorage.getUser(userId);
        Film film = filmStorage.getFilm(filmId);
        if (film.getLikes() == null || !film.getLikes().contains(userId))
            throw new FilmException("User have yet to rate the movie");
        else
            film.getLikes().remove(userId);
        return true;
    }

    public List<Film> getPopularFilms(Integer count) {
        if (count == null || count <= 0 || count > filmStorage.getFilms().size())
            count = 10;
        if (filmStorage.getFilms() == null)
            throw new FilmNotExistException("Films not exist");
        return filmStorage.getFilms().stream()
                .sorted((f1, f2) -> {
                    int likes1 = f1.getLikes() == null ? 0 : f1.getLikes().size();
                    int likes2 = f2.getLikes() == null ? 0 : f2.getLikes().size();
                    return Integer.compare(likes2, likes1);
                })
                .limit(count).toList();
    }
}
