package ru.artq.practice.kinopoisk.storage.indb.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.artq.practice.kinopoisk.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) {
        try {
            return Film.builder()
                    .id(rs.getInt("ID"))
                    .name(rs.getString("NAME"))
                    .description(rs.getString("DESCRIPTION"))
                    .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                    .duration(Duration.ofMinutes(rs.getInt("DURATION")))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to Film object", e);
        }
    }
}
