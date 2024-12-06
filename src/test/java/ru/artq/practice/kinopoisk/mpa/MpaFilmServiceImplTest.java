//package ru.artq.practice.kinopoisk.mpa;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
//import ru.artq.practice.kinopoisk.model.Film;
//import ru.artq.practice.kinopoisk.service.impl.MpaFilmServiceImpl;
//
//import java.time.Duration;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@AutoConfigureTestDatabase
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//abstract class MpaFilmServiceImplTest {
//    private final MpaFilmServiceImpl mpaFilmService;
//
//    @Test
//    void addGenreToFilm() {
//        mpaFilmService.getFilmStorage().addFilm(
//                Film.builder().name("a")
//                        .description("asd").releaseDate(LocalDate.now())
//                        .duration(Duration.ofMinutes(10)).build()
//        );
//        mpaFilmService.addMPAToFilm(1, MPA.G.name());
//        assertFalse(mpaFilmService.addMPAToFilm(1, MPA.G.name()), "Already have genre ");
//
//        assertThrows(FilmNotExistException.class,
//                () -> mpaFilmService.addMPAToFilm(0, MPA.G.name()), "No Film");
//        assertThrows(IllegalArgumentException.class,
//                () -> mpaFilmService.addMPAToFilm(1, "h"), "No genre");
//        assertThrows(IllegalArgumentException.class,
//                () -> mpaFilmService.addMPAToFilm(1, null), "No genre");
//
//        mpaFilmService.addMPAToFilm(1, MPA.PG.name());
//        mpaFilmService.addMPAToFilm(1, MPA.R.name());
//        assertEquals(3, mpaFilmService.getAllMPAFilm(1).size());
//
//        mpaFilmService.removeMPAFromFilm(1, MPA.G.name());
//        assertFalse(mpaFilmService.removeMPAFromFilm(1, MPA.G.name()), "Genre already removed");
//        System.out.println(mpaFilmService.getAllMPAFilm(1));
//        assertEquals(2, mpaFilmService.getAllMPAFilm(1).size());
//    }
//}