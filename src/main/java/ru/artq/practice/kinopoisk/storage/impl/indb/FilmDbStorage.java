package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.impl.Validation;
import ru.artq.practice.kinopoisk.storage.FilmStorage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public Film addFilm(Film film) {
        if (doesFilmExistById(film.getId()) || film.getId() != null) {
            log.debug("Film: {}, ID: {} already exist", film.getName(), film.getId());
            throw new FilmAlreadyExistException("Film already exist");
        }
        Validation.validateFilm(film);
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "NAME", film.getName(),
                "DESCRIPTION", film.getDescription(),
                "RELEASE_DATE", Date.valueOf(film.getReleaseDate()),
                "DURATION", film.getDuration().toMinutes()
        ));
        film.setId(id.intValue());
        log.info("Film {} added", film);
        return film;
    }


    @Override
    public Film updateFilm(Film film) {
        if (!doesFilmExistById(film.getId())) {
            log.debug("Film: {} doesn't exist", film.getId());
            throw new FilmNotExistException("ID Film doesn't exist");
        }
        Validation.validateFilm(film);
        jdbcTemplate.update("UPDATE FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? WHERE ID = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        log.info("Film {} updated", film);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return jdbcTemplate.query(
                "SELECT * FROM FILMS ORDER BY RELEASE_DATE DESC",
                new FilmRowMapper());
    }

    @Override
    public Film getFilmById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM FILMS WHERE FILMS.ID = ?",
                    new FilmRowMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new FilmNotExistException("Film with id: " + id + " not found", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error accessing the database for film with id: " + id, e);
        }
    }

    private static class FilmRowMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) {
            try {
                return Film.builder()
                        .id(rs.getInt("ID"))
                        .name(rs.getString("NAME"))
                        .description(rs.getString("DESCRIPTION"))
                        .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                        .duration(Duration.ofMinutes(rs.getInt("DURATION"))).build();
            } catch (SQLException e) {
                throw new RuntimeException("Error mapping result set to Film object", e);
            }
        }
    }

    private boolean doesFilmExistById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FILMS WHERE ID = ?", Integer.class, id)).orElse(0) > 0;
    }
}