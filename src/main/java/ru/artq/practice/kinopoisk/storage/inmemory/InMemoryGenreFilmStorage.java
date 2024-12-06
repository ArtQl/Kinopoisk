//package ru.artq.practice.kinopoisk.storage.inmemory;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import ru.artq.practice.kinopoisk.exception.GenreIdException;
//import ru.artq.practice.kinopoisk.exception.GenreNotFoundException;
//import ru.artq.practice.kinopoisk.model.Genre;
//import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;
//
//import java.util.*;
//
//@Slf4j
//@Component
//@Profile("in-memory")
//public class InMemoryGenreFilmStorage implements GenreFilmStorage {
//    private final Map<Integer, Genre> genres = new HashMap<>();
//    private Integer id;
//
//    private Integer setId() {
//        if (genres.isEmpty()) id = 0;
//        return ++id;
//    }
//
//    @Override
//    public Genre addGenre(Genre genre) {
//        if(genre.getId() != null)
//            throw new GenreIdException("Id have genre");
//        genre.setId(setId());
//        genres.put(genre.getId(), genre);
//        return genre;
//    }
//
//    @Override
//    public Boolean removeGenre(Genre genre) {
//        if(!genres.containsKey(genre.getId()))
//            throw new GenreNotFoundException("Genre not found");
//        return genres.remove(genre.getId());
//    }
//
//    @Override
//    public Genre updateGenre(Genre genre) {
//        if(!genres.containsKey(genre.getId()))
//            throw new GenreNotFoundException("Genre not found");
//        return genres.put(genre.getId(), genre);
//    }
//
//    @Override
//    public Collection<Genre> getAllGenres() {
//        return genres.isEmpty() ? List.of() : genres.values();
//    }
//
//    @Override
//    public Genre getGenreById(Integer id) {
//        return genres.getOrDefault(id, );
//    }
//
//
//
//    @Override
//    public Collection<Genre> getGenresFilm(Integer filmId) {
//        return List.of();
//    }
//
//    @Override
//    public Boolean addGenreToFilm(Integer filmId, Integer genreId) {
//        return null;
//    }
////
////    @Override
////    public Collection<Integer> getTopFilmByGenre(Genre genre) {
////        return List.of();
////    }
//
//
////    @Override
////    public Collection<Genre> getAllGenre(Integer filmId) {
////        return Optional.ofNullable(genreFilm.get(filmId)).orElse(Set.of());
////    }
////
////    @Override
////    public Boolean addGenre(Integer filmId, Genre genre) {
////        genreFilm.putIfAbsent(filmId, new HashSet<>());
////        if (genreFilm.get(filmId).contains(genre)) {
////            log.info("Film are already have this genre");
////            return false;
////        }
////        return genreFilm.get(filmId).add(genre);
////    }
////
////    @Override
////    public Boolean removeGenre(Integer filmId, Genre genre) {
////        if (genreFilm.get(filmId) == null || !genreFilm.get(filmId).contains(genre)) {
////            log.info("The film doesn't have the genre");
////            return false;
////        }
////        return genreFilm.get(filmId).remove(genre);
////    }
////
////    @Override
////    public Collection<Integer> getTopFilmByGenre(Genre genre) {
////        return genreFilm.entrySet().stream().filter(film -> film.getValue().contains(genre)).map(Map.Entry::getKey).toList();
////    }
//}
