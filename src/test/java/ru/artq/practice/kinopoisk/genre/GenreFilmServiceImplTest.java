package ru.artq.practice.kinopoisk.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.impl.FilmServiceImpl;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class GenreFilmServiceImplTest {
    private final FilmServiceImpl filmService;
    private final MockMvc mockMvc;

    @Test
    void addGenre() throws Exception {
        filmService.getFilmStorage().addFilm(
                new Film("a", "asdajsjfhaasdsd", LocalDate.now(), Duration.ofMinutes(10))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        genreFilmService.addGenre(1, Genre.ACTION.name());
        assertFalse(genreFilmService.addGenre(1, Genre.ACTION.name()), "Already have genre ");

        assertThrows(FilmNotExistException.class, () -> genreFilmService.addGenre(0, Genre.ACTION.name()), "No Film");
        assertThrows(IllegalArgumentException.class, () -> genreFilmService.addGenre(1, "h"), "No genre");
        assertThrows(IllegalArgumentException.class, () -> genreFilmService.addGenre(1, null), "No genre");

        genreFilmService.addGenre(1, Genre.DRAMA.name());
        genreFilmService.addGenre(1, Genre.CARTOON.name());
        assertEquals(3, genreFilmService.getAllGenre(1).size());

        genreFilmService.removeGenreFromFilm(1, Genre.DRAMA.name());
        assertFalse(genreFilmService.removeGenreFromFilm(1, Genre.DRAMA.name()), "Genre already removed");
        assertEquals(2, genreFilmService.getAllGenre(1).size());
    }
}