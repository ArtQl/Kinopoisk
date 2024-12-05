package ru.artq.practice.kinopoisk.storage;

public interface AdviceStorage {
    void adviceFilm(Integer userId, Integer friendId, Integer filmId);
}
