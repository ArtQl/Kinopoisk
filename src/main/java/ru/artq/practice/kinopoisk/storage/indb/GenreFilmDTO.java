package ru.artq.practice.kinopoisk.storage.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.HashSet;
import java.util.Set;

@Component
@Profile("db")
public class GenreFilmDTO extends AbstractEntityStorage<Genre> implements GenreFilmStorage {
    @Autowired
    public GenreFilmDTO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "GENRE", (rs, rowNum) ->
                new Genre(rs.getInt("ID"),
                        rs.getString("TITLE")));
    }

    @Override
    public Set<Integer> getGenresFilm(Integer filmId) {
        String sql = "SELECT GENRE_ID FROM FILM_GENRE WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("ID"), filmId));
    }

    @Override
    public void addGenresToFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRE WHERE FILM_ID = ?", film.getId());
        jdbcTemplate.batchUpdate(
                "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?) ",
                film.getGenres(),
                film.getGenres().size(),
                (ps, genreId) -> {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genreId);

                });
    }
}
