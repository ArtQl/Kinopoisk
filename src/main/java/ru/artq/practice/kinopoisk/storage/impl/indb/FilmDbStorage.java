package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.inter.FilmStorage;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        if (getFilms().contains(film) || film.getId() != null) {
            log.debug("Film: {}, ID: {} already exist", film.getName(), film.getId());
            throw new FilmAlreadyExistException("Film already exist");
        }
        validation(film);
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "NAME", film.getName(),
                "DESCRIPTION", film.getDescription(),
                "RELEASE_DATE", Date.valueOf(film.getReleaseDate()),
                "DURATION", film.getDuration().toMinutes()
        ));
        log.info("Film {} added", film.getName());
        film.setId(id.intValue());
        return film;
    }


    @Override
    public Film updateFilm(Film film) {
        if (!getFilms().contains(film)) {
            log.debug("Film: {} doesn't exist", film.getId());
            throw new FilmNotExistException("ID Film doesn't exist");
        }
        validation(film);
        jdbcTemplate.update("UPDATE FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? WHERE ID = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        log.info("Film {} updated", film.getName());
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        List<Integer> films = jdbcTemplate.query(
                "SELECT FILMS.ID FROM FILMS ORDER BY RELEASE_DATE DESC",
                (rs, rowNum) -> rs.getInt("id"));
        return films.isEmpty() ? Collections.emptyList()
                : films.stream().map(this::getFilmById).toList();
    }

    @Override
    public Film getFilmById(Integer id) {
        Film film = jdbcTemplate.queryForObject(
                "SELECT * FROM FILMS WHERE FILMS.ID = ?",
                (rs, rowNum) -> Film.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .releaseDate(rs.getDate("release_date").toLocalDate())
                        .duration(Duration.ofMinutes(rs.getInt("duration"))).build(),
                id);
        if (film == null)
            throw new FilmNotExistException("Film with id: " + id + ", not found");
        return film;
    }

    private void validation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Date can't be before 28 dec 1895: {} date", film.getReleaseDate());
            throw new ValidationException("Date can't be before 28 dec 1895");
        }
        if (film.getDuration().isZero() || film.getDuration().isNegative()) {
            log.debug("Duration must be positive: {} duration", film.getDuration());
            throw new ValidationException("Duration must be positive");
        }
    }
}
