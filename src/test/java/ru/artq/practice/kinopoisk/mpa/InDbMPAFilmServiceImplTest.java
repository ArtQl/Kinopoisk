package ru.artq.practice.kinopoisk.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.impl.MPAFilmServiceImpl;

@SpringBootTest
@ActiveProfiles("db")
public class InDbMPAFilmServiceImplTest extends MPAFilmServiceImplTest {
    @Autowired
    public InDbMPAFilmServiceImplTest(MPAFilmServiceImpl mpaFilmService) {
        super(mpaFilmService);
    }
}
