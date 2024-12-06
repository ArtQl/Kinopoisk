package ru.artq.practice.kinopoisk.storage.indb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ReviewIdExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.Review;
import ru.artq.practice.kinopoisk.storage.ReviewStorage;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.ReviewRowMapper;

import java.util.*;

@Component
@Profile("db")
public class ReviewDTO implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ReviewDTO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("REVIEW")
                .usingGeneratedKeyColumns("REVIEW_ID");
    }

    @Override
    public Review addReview(Review review) {
        if (review.getReviewId() != null)
            throw new ReviewIdExistException("Id review not null");
        Number id = jdbcInsert.executeAndReturnKey(Map.of(
                "FILM_ID", review.getFilmId(),
                "USER_ID", review.getUserId(),
                "CONTENT", review.getContent(),
                "IS_POSITIVE", review.getIsPositive(),
                "USEFUL", review.getUseful(),
                "USERS_LIKE", parseSet(review.getUsersLike()),
                "USERS_DISLIKE", parseSet(review.getUsersDislike())
        ));
        review.setReviewId(id.intValue());
        return review;
    }

    @Override
    public Review removeReview(Integer id) {
        Review review = getReviewById(id);
        String sql2 = "DELETE FROM REVIEW WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sql2, id);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        if (review.getReviewId() == null)
            throw new ReviewIdExistException("Review id is null");
        getReviewById(review.getReviewId());
        String sql2 = """
                    UPDATE REVIEW SET CONTENT = ?, IS_POSITIVE = ?,
                        USEFUL = ?, USERS_LIKE = ?, USERS_DISLIKE = ?
                    WHERE REVIEW_ID = ?
                """;
        jdbcTemplate.update(sql2,
                review.getContent(), review.getIsPositive(),
                review.getUseful(), review.getUsersLike().toString(),
                parseSet(review.getUsersLike()),
                parseSet(review.getUsersDislike()));
        return review;
    }

    @Override
    public Collection<Review> getAllReviewsOfFilm(Integer filmId, Integer count) {
        String sql = "SELECT * FROM REVIEW WHERE FILM_ID = ? LIMIT ?";
        Collection<Review> res = jdbcTemplate.query(sql, new ReviewRowMapper(), filmId, count);
        if (res.isEmpty()) throw new FilmNotExistException("Film not exist");
        return res;
    }

    @Override
    public Collection<Review> getAllReviewsOfUser(Integer userId, Integer count) {
        String sql = "SELECT * FROM REVIEW WHERE USER_ID = ? LIMIT ?";
        Collection<Review> res = jdbcTemplate.query(sql, new ReviewRowMapper(), userId, count);
        if (res.isEmpty()) throw new UserNotExistException("User not exist");
        return res;
    }

    @Override
    public Collection<Review> getAllReviews(Integer count) {
        String sql = "SELECT * FROM REVIEW LIMIT ?";
        Collection<Review> res = jdbcTemplate.query(sql, new ReviewRowMapper(), count);
        return res.isEmpty() ? List.of() : res;
    }

    @Override
    public Review getReviewById(Integer id) {
        String sql = "SELECT * FROM REVIEW WHERE REVIEW_ID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ReviewRowMapper(), id))
                .orElseThrow(() -> new ReviewIdExistException("Review Id not exist"));
    }

    @Override
    public Boolean likeReview(Integer id, Integer userId) {
        Review review = getReviewById(id);
        review.likeReview(userId);
        String sql = "UPDATE REVIEW SET USEFUL = ?, USERS_LIKE = ?, USERS_DISLIKE = ? WHERE REVIEW_ID = ?";
        return jdbcTemplate.update(sql, review.getUseful(),
                parseSet(review.getUsersLike()),
                parseSet(review.getUsersDislike()), id) > 0;
    }

    @Override
    public Boolean dislikeReview(Integer id, Integer userId) {
        Review review = getReviewById(id);
        review.dislikeReview(userId);
        String sql = "UPDATE REVIEW SET USEFUL = ?, USERS_LIKE = ?, USERS_DISLIKE = ? WHERE REVIEW_ID = ?";
        return jdbcTemplate.update(sql, review.getUseful(),
                parseSet(review.getUsersLike()),
                parseSet(review.getUsersDislike()), id) > 0;
    }

    @Override
    public Boolean removeLikeReview(Integer id, Integer userId) {
        Review review = getReviewById(id);
        review.removeLike(userId);
        String sql = "UPDATE REVIEW SET USEFUL = ?, USERS_LIKE = ?, USERS_DISLIKE = ? WHERE REVIEW_ID = ?";
        return jdbcTemplate.update(sql, review.getUseful(),
                parseSet(review.getUsersLike()),
                parseSet(review.getUsersDislike()), id) > 0;
    }

    @Override
    public Boolean removeDislikeReview(Integer id, Integer userId) {
        Review review = getReviewById(id);
        review.removeDislike(userId);
        String sql = "UPDATE REVIEW SET USEFUL = ?, USERS_LIKE = ?, USERS_DISLIKE = ? WHERE REVIEW_ID = ?";
        return jdbcTemplate.update(sql, review.getUseful(),
                parseSet(review.getUsersLike()),
                parseSet(review.getUsersDislike()), id) > 0;
    }

    private String parseSet(Set<Integer> set) {
        try {
            return objectMapper.writeValueAsString(set);
        } catch (Exception e) {
            return "";
        }
    }
}