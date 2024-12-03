package ru.artq.practice.kinopoisk.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.controller.FilmController;

@SpringBootTest
@ActiveProfiles("in-memory")
public class InMemoryFilmControllerTest extends FilmControllerTest {
    @Autowired
    public InMemoryFilmControllerTest(FilmController filmController) {
        super(filmController);
    }
}
