package ru.artq.practice.kinopoisk.storage.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.DirectorStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Profile("db")
public class DirectorDTO extends AbstractEntityStorage<Director> implements DirectorStorage {
    @Autowired
    public DirectorDTO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "DIRECTOR", (rs, rowNum) ->
                new Director(rs.getInt("ID"),
                        rs.getString("TITLE")));
    }

    @Override
    public Collection<Integer> getFilmsDirector(Integer directorId) {
        String sql = "SELECT FILM_ID FROM FILM_DIRECTOR WHERE DIRECTOR_ID = ?";
        Collection<Integer> films = jdbcTemplate
                .query(sql, (rs, rowNum) -> rs.getInt("FILM_ID"), directorId);
        return films.isEmpty() ? List.of() : films;
    }

    @Override
    public Set<Integer> getDirectorsFilm(Integer filmId) {
        String sql = "SELECT DIRECTOR_ID FROM FILM_DIRECTOR WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("ID"), filmId));
    }

    @Override
    public void addDirectorToFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_DIRECTOR WHERE FILM_ID = ?", film.getId());
        jdbcTemplate.batchUpdate(
                "INSERT INTO FILM_DIRECTOR (FILM_ID, DIRECTOR_ID) VALUES (?, ?)",
                film.getDirectors(), film.getDirectors().size(),
                (ps, dirId) -> {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, dirId);
                });
    }
}
