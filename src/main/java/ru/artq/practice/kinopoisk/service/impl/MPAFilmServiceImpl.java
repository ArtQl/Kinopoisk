package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.MPA;
import ru.artq.practice.kinopoisk.service.MPAFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.MPAFilmStorage;
import ru.artq.practice.kinopoisk.util.Validation;

import java.util.Collection;

@Service
@Getter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MPAFilmServiceImpl implements MPAFilmService {
    private final MPAFilmStorage mpaFilmStorage;
    private final FilmStorage filmStorage;

    @Override
    public Collection<String> getAllMPAFilm(Integer filmId) {
        filmStorage.getFilm(filmId);
        return mpaFilmStorage.getAllMPAFilm(filmId).stream().map(MPA::name).toList();
    }

    @Override
    public Boolean addMPAToFilm(Integer filmId, String mpa) {
        filmStorage.getFilm(filmId);
        return mpaFilmStorage.addMPAToFilm(filmId, Validation.validateMPA(mpa));
    }

    @Override
    public Boolean removeMPAFromFilm(Integer filmId, String mpa) {
        filmStorage.getFilm(filmId);
        return mpaFilmStorage.removeMPAFromFilm(filmId, Validation.validateMPA(mpa));
    }
}
