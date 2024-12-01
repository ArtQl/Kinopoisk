package ru.artq.practice.kinopoisk.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.artq.practice.kinopoisk.service.impl.MPAFilmServiceImpl;

@SpringBootTest
@ActiveProfiles("in-memory")
public class InMemoryMPAFilmServiceImplTest extends MPAFilmServiceImplTest {
    @Autowired
    public InMemoryMPAFilmServiceImplTest(MPAFilmServiceImpl mpaFilmService) {
        super(mpaFilmService);
    }
}
