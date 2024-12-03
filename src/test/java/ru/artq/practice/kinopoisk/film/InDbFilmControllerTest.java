package ru.artq.practice.kinopoisk.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.controller.FilmController;

@SpringBootTest
@ActiveProfiles("db")
public class InDbFilmControllerTest extends FilmControllerTest {

    @Autowired
    public InDbFilmControllerTest(FilmController filmController) {
        super(filmController);
    }
}
