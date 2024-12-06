//package ru.artq.practice.kinopoisk.genre;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
//import ru.artq.practice.kinopoisk.model.Film;
//import ru.artq.practice.kinopoisk.model.Genre;
//import ru.artq.practice.kinopoisk.service.impl.GenreFilmServiceImpl;
//
//import java.time.Duration;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@AutoConfigureTestDatabase
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//abstract class GenreFilmServiceImplTest {
//    private final GenreFilmServiceImpl genreFilmService;
//
//    @Test
//    void addGenre() {
//        genreFilmService.getFilmStorage().addFilm(
//                Film.builder().name("a")
//                        .description("asd").releaseDate(LocalDate.now())
//                        .duration(Duration.ofMinutes(10)).build()
//        );
//        genreFilmService.addGenre(1, Genre.ACTION.name());
//        assertFalse(genreFilmService.addGenre(1, Genre.ACTION.name()), "Already have genre ");
//
//        assertThrows(FilmNotExistException.class, () -> genreFilmService.addGenre(0, Genre.ACTION.name()), "No Film");
//        assertThrows(IllegalArgumentException.class, () -> genreFilmService.addGenre(1, "h"), "No genre");
//        assertThrows(IllegalArgumentException.class, () -> genreFilmService.addGenre(1, null), "No genre");
//
//        genreFilmService.addGenre(1, Genre.DRAMA.name());
//        genreFilmService.addGenre(1, Genre.CARTOON.name());
//        assertEquals(3, genreFilmService.getAllGenre(1).size());
//
//        genreFilmService.removeGenreFromFilm(1, Genre.DRAMA.name());
//        assertFalse(genreFilmService.removeGenreFromFilm(1, Genre.DRAMA.name()), "Genre already removed");
//        assertEquals(2, genreFilmService.getAllGenre(1).size());
//    }
//}