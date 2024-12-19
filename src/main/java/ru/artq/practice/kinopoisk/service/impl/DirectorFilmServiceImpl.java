package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.AbstractService;
import ru.artq.practice.kinopoisk.service.DirectorFilmService;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.DirectorStorage;

import java.util.Set;

@Service
@Getter
public class DirectorFilmServiceImpl
        extends AbstractService<Director>
        implements DirectorFilmService {
    private final DirectorStorage directorStorage;

    @Autowired
    public DirectorFilmServiceImpl(AbstractEntityStorage<Director> storage,
                                   DirectorStorage directorStorage) {
        super(storage);
        this.directorStorage = directorStorage;
    }

    @Override
    public void addDirectorToFilm(Film film) {
        if (film.getDirectors() == null || film.getDirectors().isEmpty()) return;
        directorStorage.addDirectorToFilm(film);
    }

    @Override
    public Set<Integer> getDirectorsFilm(Integer filmId) {
        return directorStorage.getDirectorsFilm(filmId);
    }
}
