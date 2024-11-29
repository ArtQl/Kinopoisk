package ru.artq.practice.kinopoisk.service;

import java.util.Collection;

public interface MPAFilmService {

    Collection<String> getAllMPAFilm(Integer filmId);

    Boolean addMPAToFilm(Integer filmId, String mpa);

    Boolean removeMPAFromFilm(Integer filmId, String mpa);
}
