//package ru.artq.practice.kinopoisk.storage.inmemory;
//
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import ru.artq.practice.kinopoisk.exception.films.LikeFilmException;
//import ru.artq.practice.kinopoisk.storage.LikeStorage;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//@Profile("in-memory")
//public class InMemoryLikeStorage implements LikeStorage {
//    private final Map<Integer, Set<Integer>> likes = new HashMap<>();
//
//    @Override
//    public Boolean likeFilm(Integer userId, Integer filmId) {
//        likes.putIfAbsent(filmId, new HashSet<>());
//        if (likes.get(filmId).contains(userId))
//            throw new LikeFilmException("User has already rated the movie");
//        else
//            return likes.get(filmId).add(userId);
//    }
//
//    @Override
//    public Boolean unlikeFilm(Integer userId, Integer filmId) {
//        if (likes.get(filmId) == null || !likes.get(filmId).contains(userId))
//            throw new LikeFilmException("User have yet to rate the movie");
//        return likes.get(filmId).remove(userId);
//    }
//
//    @Override
//    public Collection<Integer> getPopularFilms(Integer count) {
//        if (likes.isEmpty()) return List.of();
//        return likes.entrySet().stream()
//                .collect(Collectors.toMap(Map.Entry::getKey, i -> i.getValue().size()))
//                .entrySet().stream()
//                .sorted((i1, i2) -> Long.compare(i2.getValue(), i1.getValue()))
//                .map(Map.Entry::getKey)
//                .toList();
//    }
//
//    @Override
//    public Collection<Integer> getUserLikes(Integer userId) {
//        return likes.entrySet().stream()
//                .filter(film -> film.getValue().contains(userId))
//                .sorted((o1, o2) -> Integer.compare(o2.getValue().size(), o1.getValue().size()))
//                .map(Map.Entry::getKey).toList();
//    }
//
//    @Override
//    public Collection<Integer> getFilmLikes(Integer filmId) {
//        return likes.get(filmId);
//    }
//
//    @Override
//    public Collection<Integer> getCommonFilms(Integer userId, Integer friendId) {
//        Collection<Integer> userFilm = getUserLikes(userId);
//        Collection<Integer> otherFilm = getUserLikes(friendId);
//        userFilm.retainAll(otherFilm);
//        return userFilm;
//    }
//}
