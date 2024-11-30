package ru.artq.practice.kinopoisk.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.FilmService;

@SpringBootTest
@ActiveProfiles("in-memory")
public class InMemoryFilmServiceAppTest extends FilmServiceAppTest {
    @Autowired
    public InMemoryFilmServiceAppTest(FilmService filmService) {
        super(filmService);
    }
}
