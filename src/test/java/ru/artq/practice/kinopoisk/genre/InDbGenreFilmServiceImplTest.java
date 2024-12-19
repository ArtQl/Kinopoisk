package ru.artq.practice.kinopoisk.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.impl.GenreFilmServiceImpl;

@SpringBootTest
@ActiveProfiles("db")
public class InDbGenreFilmServiceImplTest extends GenreFilmServiceImplTest {
    @Autowired
    public InDbGenreFilmServiceImplTest(GenreFilmServiceImpl genreFilmService) {
        super(genreFilmService);
    }
}
