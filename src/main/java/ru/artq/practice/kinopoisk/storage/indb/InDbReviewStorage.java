package ru.artq.practice.kinopoisk.storage.indb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ReviewNotExistException;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;

import java.util.Collection;
import java.util.List;

@Component
@Profile("db")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InDbReviewStorage implements ReviewStorage {
    JdbcTemplate jdbcTemplate;
    private final RowMapper<Review> rowMapper = (rs, rowNum) -> new Review(
            rs.getInt("ID"),
            rs.getInt("FILM_ID"),
            rs.getInt("USER_ID"),
            rs.getString("REVIEW")
    );

    @Override
    public void addReviewOfFilm(Integer filmId, Integer userId, String review) {
        if(hasReview(filmId, userId))
            throw new IllegalArgumentException("Review already added");
        String sql2 = "INSERT INTO REVIEW (FILM_ID, USER_ID, REVIEW) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql2, filmId, userId, review);
    }

    @Override
    public void removeReviewOfFilm(Integer filmId, Integer userId) {
        if(!hasReview(filmId, userId))
            throw new ReviewNotExistException("Review not found");
        String sql2 = "DELETE FROM REVIEW WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql2, filmId, userId);
    }

    @Override
    public void updateReviewOfFilm(Integer filmId, Integer userId, String review) {
        if(!hasReview(filmId, userId))
            throw new ReviewNotExistException("Review not found");
        String sql2 = "UPDATE REVIEW SET REVIEW = ? WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql2, review, filmId, userId);
    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId) {
        String sql = "SELECT * FROM REVIEW WHERE FILM_ID = ?";
        Collection<Review> reviews = jdbcTemplate.query(sql, rowMapper, filmId);
        return reviews.isEmpty() ? List.of() : reviews;
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId) {
        String sql = "SELECT * FROM REVIEW WHERE USER_ID = ?";
        Collection<Review> reviews = jdbcTemplate.query(sql, rowMapper, userId);
        return reviews.isEmpty() ? List.of() : reviews;
    }

    private Boolean hasReview(Integer filmId, Integer userId) {
        String sql1 = "SELECT * FROM REVIEW WHERE FILM_ID = ? AND USER_ID = ?";
        return jdbcTemplate.query(sql1, rowMapper, filmId, userId).isEmpty();
    }
}
