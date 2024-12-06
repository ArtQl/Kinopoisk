package ru.artq.practice.kinopoisk.storage.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Mpa;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.MpaFilmStorage;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Profile("db")
public class MpaFilmDTO extends AbstractEntityStorage<Mpa> implements MpaFilmStorage {
    @Autowired
    public MpaFilmDTO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "MPA", (rs, rowNum) ->
                new Mpa(rs.getInt("ID"),
                        rs.getString("TITLE")));
    }

    @Override
    public Set<Integer> getMpaFilm(Integer filmId) {
        String sql = "SELECT MPA_ID FROM FILM_MPA WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("ID"), filmId));
    }

    @Override
    public void addMpaToFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_MPA WHERE FILM_ID = ?", film.getId());
        jdbcTemplate.batchUpdate(
                "INSERT INTO FILM_MPA (FILM_ID, MPA_ID) VALUES (?, ?) ",
                film.getMpa(),
                film.getMpa().size(),
                (ps, mpaId) -> {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, mpaId);

                });
    }
}
