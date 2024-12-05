package ru.artq.practice.kinopoisk.storage.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.storage.DirectorStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Profile("db")
public class InDbDirectorStorage implements DirectorStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Director> rowMapper = (rs, rowNow) -> new Director(
            rs.getInt("ID"),
            rs.getString("NAME"),
            rs.getDate("BIRTHDAY").toLocalDate()
    );

    @Autowired
    public InDbDirectorStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("DIRECTOR")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public Director addDirector(Director director) {
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "NAME", director.getName(),
                "BIRTHDAY", director.getBirthday()
        ));
        director.setId(id.intValue());
        return director;
    }

    @Override
    public Director updateDirector(Director director) {
        String sql = "UPDATE DIRECTOR SET NAME = ?, BIRTHDAY = ? WHERE ID = ?";
        jdbcTemplate.update(sql, director.getName(), director.getBirthday(), director.getId());
        return director;
    }

    @Override
    public Boolean deleteDirector(Integer directorId) {
        String sql = "DELETE FROM DIRECTOR WHERE ID = ?";
        return jdbcTemplate.update(sql, directorId) > 0;
    }

    @Override
    public Collection<Director> getDirectors() {
        String sql = "SELECT * FROM DIRECTOR";
        Collection<Director> dirs = jdbcTemplate.query(sql, rowMapper);
        return dirs.isEmpty() ? List.of() : dirs;
    }

    @Override
    public Director getDirector(Integer directorId) {
        String sql = "SELECT * FROM DIRECTOR WHERE ID = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, rowMapper, directorId))
                .orElseThrow(() -> new IllegalArgumentException("Director not exist"));
    }

    @Override
    public void addDirectorToFilm(Integer filmId, Integer directorId) {
        String sql = "INSERT INTO DIRECTOR_FILM (FILM_ID, DIRECTOR_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, directorId);
    }

    @Override
    public void updateDirectorToFilm(Integer filmId, Integer directorId) {
        String sql = "UPDATE DIRECTOR_FILM SET DIRECTOR_ID = ? WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, directorId, filmId);
    }

    @Override
    public void deleteDirectorToFilm(Integer filmId, Integer directorId) {
        String sql = "DELETE FROM DIRECTOR_FILM WHERE FILM_ID = ? AND DIRECTOR_ID = ?";
        jdbcTemplate.update(sql, filmId, directorId);
    }

    @Override
    public Collection<Integer> getFilmsOfDirector(Integer directorId) {
        String sql = "SELECT FILM_ID FROM DIRECTOR_FILM WHERE DIRECTOR_ID = ?";
        Collection<Integer> films = jdbcTemplate
                .query(sql, (rs, rowNum) -> rs.getInt("FILM_ID"), directorId);
        return films.isEmpty() ? List.of() : films;
    }

    private Boolean hasExistDirector(Integer directorId) {
        String sql = "SELECT COUNT(*) FROM DIRECTOR WHERE ID = ?";
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(sql, Integer.class, directorId)).orElse(0) > 0;
    }
}
