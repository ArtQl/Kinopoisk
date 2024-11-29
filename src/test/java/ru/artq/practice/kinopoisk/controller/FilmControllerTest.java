package ru.artq.practice.kinopoisk.controller;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmControllerTest {
//    @Autowired
//    FilmController filmController;
//    Film film = Film.builder()
//            .name("a")
//            .description("1234567891")
//            .duration(Duration.ofMinutes(30))
//            .releaseDate(LocalDate.of(2020, 12, 31))
//            .build();
//
//    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    private final Validator validator = factory.getValidator();
//
//    @AfterEach
//    void clear() {
//        filmController.getFilms().clear();
//    }
//
//    @Test
//    void addFilm() {
//        assertThrows(InvalidFilmIdException.class, () -> filmController.addFilm(Film.builder().name("a").description("a").duration(Duration.ofMinutes(30)).releaseDate(LocalDate.of(2020, 12, 31)).id(1).build()), "Have id film");
//
//        Set<ConstraintViolation<Film>> violations = validator.validate(Film.builder().build());
//        assertEquals(4, violations.size(), "empty film");
//
//        assertThrows(ValidationException.class, () -> filmController.addFilm(Film.builder().name("a").description("a")
//                .duration(Duration.ofMinutes(-21))
//                .releaseDate(LocalDate.of(2020, 12, 31))
//                .build()), "Negative duration");
//
//        assertEquals(0, validator.validate(film).size());
//        assertDoesNotThrow(() -> filmController.addFilm(film), "Fill film");
//    }
//
//    @Test
//    void updateFilm() {
//        filmController.addFilm(film);
//        film.setId(12);
//        assertThrows(InvalidFilmIdException.class, () -> filmController.updateFilm(film));
//    }
//
//    @Test
//    void getFilms() {
//        assertThrows(FilmNotExistException.class, () -> filmController.getFilms());
//        filmController.addFilm(film);
//        assertEquals(1, filmController.getFilms().size());
//    }
}