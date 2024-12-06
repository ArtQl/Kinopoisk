package ru.artq.practice.kinopoisk.storage.indb.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.artq.practice.kinopoisk.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashSet;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) {
        try {
            return new Film(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getDate("RELEASE_DATE").toLocalDate(),
                    Duration.ofMinutes(rs.getInt("DURATION")),
                    new HashSet<>(), new HashSet<>(), new HashSet<>()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to Film object", e);
        }
    }
}
