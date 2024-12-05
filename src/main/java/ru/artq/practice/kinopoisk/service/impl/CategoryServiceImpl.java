package ru.artq.practice.kinopoisk.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.CategoryService;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Getter
public class CategoryServiceImpl implements CategoryService {
    FilmStorage filmStorage;
    GenreFilmStorage genreFilmStorage;

    @Override
    public Collection<Film> getTopFilmByGenre(String genre) {
        Collection<Integer> idFilms = genreFilmStorage
                .getTopFilmByGenre(ValidationService.validateGenre(genre));
        return idFilms.stream().map(filmStorage::getFilm).toList();
    }

    @Override
    public Collection<Film> getTopFilmByYear(Integer year) {
        return filmStorage.getTopFilmByYear(year);
    }
}
