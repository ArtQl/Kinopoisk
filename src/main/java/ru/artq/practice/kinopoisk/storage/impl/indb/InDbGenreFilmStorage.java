package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.GenreNotFoundException;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.storage.GenreFilmStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class InDbGenreFilmStorage implements GenreFilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Genre> ROW_MAPPER = (rs, rowNum) -> Genre.valueOf(rs.getString("TITLE"));

    @Autowired
    public InDbGenreFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getAllGenreFilm(Integer filmId) {
        String sql = """
                SELECT GENRE.TITLE FROM FILM_GENRE
                JOIN GENRE ON FILM_GENRE.GENRE_ID = GENRE.ID
                WHERE FILM_ID = ?
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, filmId);
    }

    @Override
    public Boolean addGenreToFilm(Integer filmId, Genre genre) {
        if (hasGenre(filmId, genre)) {
            log.info("Film {} already has genre {}", filmId, genre);
            return false;
        }
        String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, getGenreId(genre)) > 0;
    }

    @Override
    public Boolean removeGenreFromFilm(Integer filmId, Genre genre) {
        if (hasGenre(filmId, genre)) {
            log.warn("Film {} does not have genre {}", filmId, genre);
            throw new GenreNotFoundException("The film doesn't have the genre");
        }
        String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ? AND GENRE_ID = ?";
        return jdbcTemplate.update(sql, filmId, getGenreId(genre)) > 0;
    }

    private Boolean hasGenre(Integer filmId, Genre genre) {
        String sql = """
                SELECT COUNT(*) FROM FILM_GENRE
                JOIN GENRE ON FILM_GENRE.GENRE_ID = GENRE.ID
                WHERE FILM_ID = ? AND TITLE = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class, filmId, genre)).orElse(0) > 0;
    }

    private Integer getGenreId(Genre genre) {
        String sql = "SELECT ID FROM GENRE WHERE TITLE = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, Integer.class, genre))
                .orElseThrow(() -> new GenreNotFoundException("Genre " + genre + " not found"));
    }
}
