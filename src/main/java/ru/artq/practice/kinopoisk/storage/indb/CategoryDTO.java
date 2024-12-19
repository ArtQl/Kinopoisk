package ru.artq.practice.kinopoisk.storage.indb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.storage.CategoryStorage;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.FilmRowMapper;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile("db")
public class CategoryDTO implements CategoryStorage {
    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> getTopFilmByYear(Integer year) {
        String sql = "SELECT * FROM FILMS WHERE YEAR(RELEASE_DATE) = ?";
        Collection<Film> films = jdbcTemplate.query(sql, new FilmRowMapper(), year);
        if (films.isEmpty()) {
            log.debug("No films found for the year {}", year);
            throw new FilmNotExistException("No films found for the specified year: " + year);
        }
        return films;
    }

    @Override
    public Collection<Film> getTopFilmByGenre(String genre) {
        String sql = """
                SELECT FILMS.* FROM FILMS
                JOIN FILM_GENRE ON FILMS.ID = FILM_GENRE.FILM_ID
                WHERE GENRE_ID = ?
                """;
        return jdbcTemplate.query(sql, new FilmRowMapper(), genre);
    }

    @Override
    public Collection<Film> getFilmsDirector(Integer directorId, String sortBy) {
        String sql = """
                SELECT FILMS.* FROM FILMS
                JOIN FILM_DIRECTOR ON FILMS.ID = FILM_DIRECTOR.FILM_ID
                JOIN LIKES ON FILMS.ID = LIKES.FILM_ID
                WHERE DIRECTOR_ID = ?
                """;
        if (sortBy.equals("likes")) sql += " ORDER BY COUNT(USER_ID) DESC";
        else sql += " ORDER BY FILMS.RELEASE_DATE DESC";
        return jdbcTemplate.query(sql, new FilmRowMapper(), directorId);
    }
}
