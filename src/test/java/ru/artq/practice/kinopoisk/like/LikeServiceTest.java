//package ru.artq.practice.kinopoisk.like;
//
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
//import ru.artq.practice.kinopoisk.exception.films.LikeFilmException;
//import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
//import ru.artq.practice.kinopoisk.model.Film;
//import ru.artq.practice.kinopoisk.model.User;
//import ru.artq.practice.kinopoisk.service.impl.LikeFilmServiceImpl;
//
//import java.time.Duration;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureTestDatabase
//@AllArgsConstructor(onConstructor_ = @Autowired)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//abstract class LikeServiceTest {
//    private final LikeFilmServiceImpl likeFilmService;
//
//    @BeforeEach
//    void start() {
//        for (int i = 1; i <= 10; i++) {
//            likeFilmService.getFilmStorage().addFilm(
//                    Film.builder()
//                            .name("a" + i).description("1234567891")
//                            .duration(Duration.ofMinutes(30))
//                            .releaseDate(LocalDate.of(2020, 12, 31))
//                            .build());
//            likeFilmService.getUserStorage().addUser(
//                    User.builder()
//                            .username("Art" + i)
//                            .email("asedmail.ru")
//                            .login("Arte" + i)
//                            .birthday(LocalDate.now())
//                            .build());
//        }
//    }
//
//    @Test
//    void likeUnlikeFilm() {
//        likeFilmService.likeFilm(1, 1);
//        assertEquals(1, likeFilmService.getPopularFilms(10).size());
//
//        assertThrows(LikeFilmException.class, () -> likeFilmService.likeFilm(1, 1), "Like already");
//        assertThrows(FilmNotExistException.class, () -> likeFilmService.likeFilm(1, -1), "No Film");
//        assertThrows(UserNotExistException.class, () -> likeFilmService.likeFilm(-1, 1), "No User");
//
//        likeFilmService.unlikeFilm(1, 1);
//        assertTrue(likeFilmService.getPopularFilms(10).isEmpty());
//        assertThrows(LikeFilmException.class, () -> likeFilmService.unlikeFilm(1, 1), "no like");
//        assertThrows(FilmNotExistException.class, () -> likeFilmService.unlikeFilm(1, -1), "no Film");
//        assertThrows(UserNotExistException.class, () -> likeFilmService.unlikeFilm(-1, 1), "no User");
//    }
//
//    @Test
//    void getPopularFilm() {
//        assertTrue(likeFilmService.getPopularFilms(10).isEmpty());
//        assertTrue(likeFilmService.getUserLikes(1).isEmpty());
//        likeFilmService.likeFilm(1, 1);
//        likeFilmService.likeFilm(1, 2);
//        likeFilmService.likeFilm(1, 3);
//        assertEquals(3, likeFilmService.getUserLikes(1).size());
//        likeFilmService.likeFilm(2, 2);
//        likeFilmService.likeFilm(2, 3);
//        likeFilmService.likeFilm(2, 5);
//        likeFilmService.likeFilm(3, 4);
//        likeFilmService.likeFilm(3, 2);
//        assertEquals(2, likeFilmService.getPopularFilms(6).stream().toList().getFirst().getId());
//        assertEquals(5, likeFilmService.getPopularFilms(6).stream().toList().getLast().getId());
//
//        likeFilmService.unlikeFilm(1, 1);
//        likeFilmService.unlikeFilm(1, 2);
//        likeFilmService.unlikeFilm(2, 2);
//        likeFilmService.unlikeFilm(2, 5);
//        assertEquals(3, likeFilmService.getPopularFilms(5).stream().toList().getFirst().getId());
//        assertEquals(4, likeFilmService.getPopularFilms(5).stream().toList().getLast().getId());
//    }
//}
