package ru.artq.practice.kinopoisk.storage.impl.inmemory;

import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.LikeFilmException;
import ru.artq.practice.kinopoisk.storage.LikeStorage;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("inMemoryLikeStorage")
public class InMemoryLikeStorage implements LikeStorage {
    Map<Integer, Set<Integer>> userLikes = new HashMap<>();

    @Override
    public Boolean likeFilm(Integer userId, Integer filmId) {
        userLikes.putIfAbsent(userId, new HashSet<>());
        if (userLikes.get(userId).contains(filmId))
            throw new LikeFilmException("User has already rated the movie");
        else
            return userLikes.get(userId).add(filmId);
    }

    @Override
    public Boolean unlikeFilm(Integer userId, Integer filmId) {
        if (userLikes.get(userId) == null || !userLikes.get(userId).contains(filmId))
            throw new LikeFilmException("User have yet to rate the movie");
        else
            return userLikes.get(userId).remove(filmId);
    }

    @Override
    public Collection<Integer> getPopularFilms(Integer count) {
        if(userLikes.isEmpty()) return List.of();
        return userLikes.values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((i1, i2) -> Long.compare(i2.getValue(), i1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Collection<Integer> getUserLikes(Integer userId) {
        return Optional.ofNullable(userLikes.get(userId)).orElse(Set.of());
    }
}
