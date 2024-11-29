package ru.artq.practice.kinopoisk.service;

public class FilmServiceImplTest {
//    FilmServiceImpl filmServiceImpl;
//
//    @BeforeEach
//    void start() {
//        filmServiceImpl = new FilmServiceImpl(new InMemoryFilmStorage());
//        for (int i = 1; i <= 10; i++) {
//            filmServiceImpl.getFilmStorage().addFilm(Film.builder()
//                    .name("a" + i).description("1234567891")
//                    .duration(Duration.ofMinutes(30))
//                    .releaseDate(LocalDate.of(2020,12,31))
//                    .build());
//            filmServiceImpl.getUserStorage().addUser(User.builder().email("adf@mail.ru").login("a" + i).birthday(LocalDate.now()).build());
//        }
//    }
//
//    @Test
//    void likeUnlikeFilm() {
//        filmServiceImpl.likeFilm(1, 1);
//        assertThrows(LikeFilmException.class, () -> filmServiceImpl.likeFilm(1, 1), "Like already");
//        assertThrows(FilmNotExistException.class, () -> filmServiceImpl.likeFilm(-1, 1), "No Film");
//        assertThrows(UserNotExistException.class, () -> filmServiceImpl.likeFilm(1, -1), "No User");
//
//        filmServiceImpl.unlikeFilm(1,1);
//        assertTrue(filmServiceImpl.getFilmStorage().getFilmById(1).getLikes().isEmpty());
//        assertThrows(LikeFilmException.class, () -> filmServiceImpl.unlikeFilm(1,1), "no like");
//        assertThrows(FilmNotExistException.class, () -> filmServiceImpl.unlikeFilm(-1,1), "no Film");
//        assertThrows(UserNotExistException.class, () -> filmServiceImpl.unlikeFilm(1,-1), "no User");
//    }
//
//    @Test
//    void getPopularFilm() {
//        assertEquals(10, filmServiceImpl.getPopularFilms(100).size());
//        assertEquals(10, filmServiceImpl.getPopularFilms(null).size());
//        assertEquals(10, filmServiceImpl.getPopularFilms(-19).size());
//
//        filmServiceImpl.likeFilm(1,1);
//        filmServiceImpl.likeFilm(1,2);
//        filmServiceImpl.likeFilm(1,3);
//        filmServiceImpl.likeFilm(2,2);
//        filmServiceImpl.likeFilm(2,3);
//        filmServiceImpl.likeFilm(3,3);
//        filmServiceImpl.likeFilm(6,3);
//        filmServiceImpl.likeFilm(7,3);
//        assertEquals(1, filmServiceImpl.getPopularFilms(6).getFirst().getId());
//        assertEquals(4, filmServiceImpl.getPopularFilms(6).getLast().getId());
//
//        filmServiceImpl.unlikeFilm(1,1);
//        filmServiceImpl.unlikeFilm(1,2);
//        filmServiceImpl.unlikeFilm(6,3);
//        assertEquals(2, filmServiceImpl.getPopularFilms(5).getFirst().getId());
//        assertEquals(4, filmServiceImpl.getPopularFilms(5).getLast().getId());
//    }
}
