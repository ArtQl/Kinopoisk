package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Mpa;
import ru.artq.practice.kinopoisk.service.AbstractService;
import ru.artq.practice.kinopoisk.service.MpaFilmService;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.MpaFilmStorage;

import java.util.Collection;
import java.util.Set;

@Service
@Getter
public class MpaFilmServiceImpl extends AbstractService<Mpa> implements MpaFilmService {
    private final MpaFilmStorage mpaFilmStorage;

    @Autowired
    public MpaFilmServiceImpl(AbstractEntityStorage<Mpa> storage, MpaFilmStorage mpaFilmStorage) {
        super(storage);
        this.mpaFilmStorage = mpaFilmStorage;
    }

    @Override
    public void addMpaToFilm(Film film) {
        if (film.getMpa().isEmpty()) return;
        mpaFilmStorage.addMpaToFilm(film);
    }

    @Override
    public Set<Integer> getMpaFilm(Integer filmId) {
        return mpaFilmStorage.getMpaFilm(filmId);
    }
}
