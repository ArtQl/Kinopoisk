package ru.artq.practice.kinopoisk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.artq.practice.kinopoisk.exception.films.FilmException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.User;
import ru.artq.practice.kinopoisk.storage.film.InMemoryFilmStorage;
import ru.artq.practice.kinopoisk.storage.user.InMemoryUserStorage;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {
    FilmService filmService;

    @BeforeEach
    void start() {
        filmService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
        for (int i = 1; i <= 10; i++) {
            filmService.getFilmStorage().addFilm(Film.builder()
                    .name("a" + i).description("1234567891")
                    .duration(Duration.ofMinutes(30))
                    .releaseDate(LocalDate.of(2020,12,31))
                    .build());
            filmService.getUserStorage().addUser(User.builder().email("adf@mail.ru").login("a" + i).birthday(LocalDate.now()).build());
        }
    }

    @Test
    void likeUnlikeFilm() {
        filmService.likeFilm(1, 1);
        assertThrows(FilmException.class, () -> filmService.likeFilm(1, 1), "Like already");
        assertThrows(FilmNotExistException.class, () -> filmService.likeFilm(-1, 1), "No Film");
        assertThrows(UserNotExistException.class, () -> filmService.likeFilm(1, -1), "No User");

        filmService.unlikeFilm(1,1);
        assertTrue(filmService.getFilmStorage().getFilm(1).getLikes().isEmpty());
        assertThrows(FilmException.class, () -> filmService.unlikeFilm(1,1), "no like");
        assertThrows(FilmNotExistException.class, () -> filmService.unlikeFilm(-1,1), "no Film");
        assertThrows(UserNotExistException.class, () -> filmService.unlikeFilm(1,-1), "no User");
    }

    @Test
    void getPopularFilm() {
        assertEquals(10, filmService.getPopularFilms(100).size());
        assertEquals(10, filmService.getPopularFilms(null).size());
        assertEquals(10, filmService.getPopularFilms(-19).size());

        filmService.likeFilm(1,1);
        filmService.likeFilm(1,2);
        filmService.likeFilm(1,3);
        filmService.likeFilm(2,2);
        filmService.likeFilm(2,3);
        filmService.likeFilm(3,3);
        filmService.likeFilm(6,3);
        filmService.likeFilm(7,3);
        assertEquals(1, filmService.getPopularFilms(6).getFirst().getId());
        assertEquals(4, filmService.getPopularFilms(6).getLast().getId());

        filmService.unlikeFilm(1,1);
        filmService.unlikeFilm(1,2);
        filmService.unlikeFilm(6,3);
        assertEquals(2, filmService.getPopularFilms(5).getFirst().getId());
        assertEquals(4, filmService.getPopularFilms(5).getLast().getId());
    }
}
