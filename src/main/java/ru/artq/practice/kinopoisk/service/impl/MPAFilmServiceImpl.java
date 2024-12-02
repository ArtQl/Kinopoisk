package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.MPA;
import ru.artq.practice.kinopoisk.service.MPAFilmService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.MPAFilmStorage;

import java.util.Collection;

@Service
@Getter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MPAFilmServiceImpl implements MPAFilmService {
    private final MPAFilmStorage mpaFilmStorage;
    private final FilmStorage filmStorage;

    @Override
    public Collection<String> getAllMPAFilm(Integer filmId) {
        return mpaFilmStorage
                .getAllMPAFilm(filmStorage.getFilm(filmId).getId())
                .stream().map(MPA::name).toList();
    }

    @Override
    public Boolean addMPAToFilm(Integer filmId, String mpa) {
        return mpaFilmStorage.addMPAToFilm(
                filmStorage.getFilm(filmId).getId(),
                ValidationService.validateMPA(mpa));
    }

    @Override
    public Boolean removeMPAFromFilm(Integer filmId, String mpa) {
        return mpaFilmStorage.removeMPAFromFilm(
                filmStorage.getFilm(filmId).getId(),
                ValidationService.validateMPA(mpa));
    }
}
