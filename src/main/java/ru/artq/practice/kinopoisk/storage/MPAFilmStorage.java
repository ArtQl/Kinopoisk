package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.MPA;

import java.util.Collection;

public interface MPAFilmStorage {

    Collection<MPA> getAllMPAFilm(Integer filmId);

    Boolean addMPAToFilm(Integer filmId, MPA mpa);

    Boolean removeMPAFromFilm(Integer filmId, MPA mpa);
}
