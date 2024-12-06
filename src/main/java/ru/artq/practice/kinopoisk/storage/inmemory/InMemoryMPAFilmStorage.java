//package ru.artq.practice.kinopoisk.storage.inmemory;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import ru.artq.practice.kinopoisk.model.MPA;
//import ru.artq.practice.kinopoisk.storage.MPAFilmStorage;
//
//import java.util.*;
//
//@Slf4j
//@Component
//@Profile("in-memory")
//public class InMemoryMPAFilmStorage implements MPAFilmStorage {
//    private final Map<Integer, Set<MPA>> mpaFilm = new HashMap<>();
//
//    @Override
//    public Collection<MPA> getAllMPAFilm(Integer filmId) {
//        return Optional.ofNullable(mpaFilm.get(filmId)).orElse(Set.of());
//    }
//
//    @Override
//    public Boolean addMPAToFilm(Integer filmId, MPA mpa) {
//        mpaFilm.putIfAbsent(filmId, new HashSet<>());
//        if (mpaFilm.get(filmId).contains(mpa)) {
//            log.info("Film are already have this genre");
//            return false;
//        }
//        return mpaFilm.get(filmId).add(mpa);
//    }
//
//    @Override
//    public Boolean removeMpaFromFilm(Integer filmId, MPA mpa) {
//        if (mpaFilm.get(filmId) == null || !mpaFilm.get(filmId).contains(mpa)) {
//            log.info("The film doesn't have the genre");
//            return false;
//        }
//        return mpaFilm.get(filmId).remove(mpa);
//    }
//}
