package ru.artq.practice.kinopoisk.storage.indb.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.artq.practice.kinopoisk.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) {
        try {
            return new User(
                    rs.getInt("ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getDate("BIRTHDAY").toLocalDate(),
                    rs.getString("USERNAME")
                    );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to User object", e);
        }
    }
}
