package ru.artq.practice.kinopoisk.storage.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.FilmRowMapper;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public Film addFilm(Film film) {
        if (doesFilmExistById(film.getId())) {
            log.debug("Film: {} already exist", film.getName());
            throw new FilmAlreadyExistException("Film already exist");
        }
        if (film.getId() != null) {
            log.debug("ID Film: {} already exist", film.getId());
            throw new InvalidFilmIdException("ID Film already exist");
        }

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
            log.debug("ID Film: {} doesn't exist", film.getId());
            throw new InvalidFilmIdException("ID Film doesn't exist");
        }
        String sql = "UPDATE FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? WHERE ID = ?";
        jdbcTemplate.update(sql,
                film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(),
                film.getId());
        log.info("Film {} updated", film);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT * FROM FILMS ORDER BY RELEASE_DATE DESC";
        Collection<Film> films = jdbcTemplate.query(sql, new FilmRowMapper());
        if (films.isEmpty()) {
            log.debug("Films no added");
            throw new FilmNotExistException("Films no added");
        }
        return films;
    }

    @Override
    public Film getFilm(Integer id) {
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

    private boolean doesFilmExistById(Integer id) {
        return Optional.ofNullable(jdbcTemplate
                        .queryForObject("SELECT COUNT(*) FROM FILMS WHERE ID = ?", Integer.class, id))
                .orElse(0) > 0;
    }
}