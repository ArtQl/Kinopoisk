package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.FilmStorage;
import ru.artq.practice.kinopoisk.storage.impl.indb.rowmapper.FilmRowMapper;
import ru.artq.practice.kinopoisk.util.Validation;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("inDbFilmStorage")
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
    public Film getFilm(Integer id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM FILMS WHERE ID = ?",
                    new FilmRowMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new FilmNotExistException("Film with id: " + id + " not found", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error accessing the database for film with id: " + id, e);
        }
    }

    private boolean doesFilmExistById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FILMS WHERE ID = ?", Integer.class, id)).orElse(0) > 0;
    }
}