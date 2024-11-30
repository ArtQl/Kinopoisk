package ru.artq.practice.kinopoisk.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.FilmService;

@SpringBootTest
@ActiveProfiles("db")
public class InDbFilmServiceAppTest extends FilmServiceAppTest {
    @Autowired
    public InDbFilmServiceAppTest(FilmService filmService) {
        super(filmService);
    }

}
