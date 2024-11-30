package ru.artq.practice.kinopoisk.storage.impl.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.LikeFilmException;
import ru.artq.practice.kinopoisk.storage.LikeStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Profile("db")
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Integer> rowMapper = (rs, rowNum) -> rs.getInt("FILM_ID");

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Integer> getUserLikes(Integer userId) {
        String sql = "SELECT * FROM LIKES WHERE USER_ID = ?";
        List<Integer> res = jdbcTemplate.query(sql, rowMapper, userId);
        return res.isEmpty() ? Collections.emptyList() : res;
    }

    @Override
    public Boolean likeFilm(Integer userId, Integer filmId) {
        if (doesLikeExist(filmId, userId))
            throw new LikeFilmException("User has already rated the movie");
        String sql = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    @Override
    public Boolean unlikeFilm(Integer userId, Integer filmId) {
        if (!doesLikeExist(filmId, userId))
            throw new LikeFilmException("User have yet to rate the movie");
        String sql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    @Override
    public Collection<Integer> getPopularFilms(Integer count) {
        String sql = "SELECT FILM_ID FROM LIKES GROUP BY FILM_ID ORDER BY COUNT(USER_ID) DESC LIMIT ?";
        return jdbcTemplate.query(sql, rowMapper, count);
    }

    @Override
    public Collection<Integer> getFilmLikes(Integer filmId) {
        //todo
        return List.of();
    }

    private Boolean doesLikeExist(Integer filmId, Integer userId) {
        String sql = "SELECT COUNT(*) FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        return Optional.ofNullable(jdbcTemplate
                        .queryForObject(sql, Integer.class, filmId, userId))
                .orElse(0) > 0;
    }
}
