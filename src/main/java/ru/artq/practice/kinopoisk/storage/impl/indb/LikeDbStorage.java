package ru.artq.practice.kinopoisk.storage.impl.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.LikeFilmException;
import ru.artq.practice.kinopoisk.storage.LikeStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component("inDbLikeStorage")
public class LikeDbStorage implements LikeStorage {
    JdbcTemplate jdbcTemplate;
    RowMapper<Integer> rowMapperToInt = (rs, rowNum) -> rs.getInt("FILM_ID");

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Integer> getUserLikes(Integer userId) {
        String sql = "SELECT * FROM LIKES WHERE USER_ID = ?";
        List<Integer> res = jdbcTemplate.query(sql, rowMapperToInt, userId);
        return res.isEmpty() ? Set.of() : new HashSet<>(res);
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
    public List<Integer> getPopularFilms(Integer count) {
        String sql = """
                SELECT FILMS.*, COUNT(USER_ID) rate
                FROM LIKES
                JOIN FILMS ON LIKES.FILM_ID = FILMS.ID
                GROUP BY FILM_ID ORDER BY rate DESC LIMIT ?
                """;
//        return jdbcTemplate.query(sql, new FilmRowMapper(), count);
        return List.of();
    }

    private Boolean doesLikeExist(Integer filmId, Integer userId) {
        String sql = "SELECT COUNT(*) FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        return Optional.ofNullable(jdbcTemplate
                        .queryForObject(sql, Integer.class, filmId, userId))
                .orElse(0) > 0;
    }
}
