package ru.artq.practice.kinopoisk.storage.indb.rowmapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import ru.artq.practice.kinopoisk.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ReviewRowMapper implements RowMapper<Review> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Review mapRow(ResultSet rs, int rowNum) {
        try {
            return new Review(
                    rs.getInt("REVIEW_ID"),
                    rs.getInt("FILM_ID"),
                    rs.getInt("USER_ID"),
                    rs.getString("CONTENT"),
                    rs.getBoolean("IS_POSITIVE"),
                    rs.getInt("USEFUL"),
                    parseJson(rs.getString("USERS_LIKE")),
                    parseJson(rs.getString("USERS_DISLIKE"))
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to Review object", e);
        }
    }

    private Set<Integer> parseJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return Set.of();
        }
    }
}
