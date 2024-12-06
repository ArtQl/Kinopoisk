//package ru.artq.practice.kinopoisk.storage.inmemory;
//
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import ru.artq.practice.kinopoisk.model.Director;
//import ru.artq.practice.kinopoisk.storage.DirectorStorage;
//
//import java.util.*;
//
//@Component
//@Profile("in-memory")
//public class InMemoryDirectorStorage implements DirectorStorage {
//    Map<Integer, Director> directors = new HashMap<>();
//    Map<Integer, Set<Integer>> directorsFilms = new HashMap<>();
//    Integer id;
//
//    private Integer setId() {
//        if (directors.isEmpty()) id = 0;
//        return ++id;
//    }
//
//    @Override
//    public Director addDirector(Director director) {
//        if (director.getId() != null)
//            throw new IllegalArgumentException("DirectorId already assigned");
//        director.setId(setId());
//        directors.put(director.getId(), director);
//        directorsFilms.put(director.getId(), new HashSet<>());
//        return director;
//    }
//
//    @Override
//    public Director updateDirector(Director director) {
//        if (director.getId() == null)
//            throw new IllegalArgumentException("DirectorId is null");
//        if (!directors.containsKey(director.getId()))
//            throw new IllegalArgumentException("Director no added");
//        directors.put(director.getId(), director);
//        return director;
//    }
//
//    @Override
//    public Boolean deleteDirector(Integer directorId) {
//        if (!directors.containsKey(directorId))
//            throw new IllegalArgumentException("Director no added");
//        directorsFilms.remove(directorId);
//        directors.remove(directorId);
//        return true;
//    }
//
//    @Override
//    public Collection<Director> getDirectors() {
//        if (directors.isEmpty()) return List.of();
//        return directors.values();
//    }
//
//    @Override
//    public Director getDirector(Integer directorId) {
//        if (!directors.containsKey(directorId))
//            throw new IllegalArgumentException("Director no added");
//        return directors.get(directorId);
//    }
//
//    @Override
//    public void addDirectorToFilm(Integer filmId, Integer directorId) {
//        if (!directors.containsKey(directorId))
//            throw new IllegalArgumentException("Director no added");
//        directorsFilms.get(directorId).add(filmId);
//    }
//
//    @Override
//    public void updateDirectorToFilm(Integer filmId, Integer directorId) {
//        if (!directors.containsKey(directorId))
//            throw new IllegalArgumentException("Director no added");
//
//        Optional<Integer> pastDir = directorsFilms.entrySet().stream()
//                .filter(film -> film.getValue().contains(filmId))
//                .map(Map.Entry::getKey).findFirst();
//
//        pastDir.ifPresent(past -> {
//            Set<Integer> film = directorsFilms.get(past);
//            film.remove(filmId);
//            if (film.isEmpty()) directorsFilms.remove(past);
//        });
//
//        directorsFilms.get(directorId).add(filmId);
//    }
//
//    @Override
//    public void deleteDirectorToFilm(Integer filmId, Integer directorId) {
//        //todo
//    }
//
//    @Override
//    public Collection<Integer> getFilmsOfDirector(Integer directorId) {
//        if (!directors.containsKey(directorId))
//            throw new IllegalArgumentException("Director no added");
//        return directorsFilms.get(directorId);
//    }
//}
