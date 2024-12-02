package ru.artq.practice.kinopoisk.storage.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.MPANotFoundException;
import ru.artq.practice.kinopoisk.model.MPA;
import ru.artq.practice.kinopoisk.storage.MPAFilmStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class InDbMPAFilmStorage implements MPAFilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<MPA> ROW_MAPPER = (rs, rowNum) -> MPA.valueOf(rs.getString("TITLE"));

    @Autowired
    public InDbMPAFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<MPA> getAllMPAFilm(Integer filmId) {
        String sql = """
                SELECT MPA.TITLE FROM FILM_MPA
                JOIN MPA ON FILM_MPA.MPA_ID = MPA.ID
                WHERE FILM_ID = ?
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER, filmId);
    }

    @Override
    public Boolean addMPAToFilm(Integer filmId, MPA mpa) {
        if (hasMPA(filmId, mpa)) {
            log.info("Film {} already has MPA {}", filmId, mpa);
            return false;
        }
        String sql = "INSERT INTO FILM_MPA (FILM_ID, MPA_ID) VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, getMPAId(mpa)) > 0;
    }

    @Override
    public Boolean removeMPAFromFilm(Integer filmId, MPA mpa) {
        if (!hasMPA(filmId, mpa)) {
            log.warn("Film {} does not have mpa {}", filmId, mpa);
            return false;
        }
        String sql = "DELETE FROM FILM_MPA WHERE FILM_ID = ? AND MPA_ID = ?";
        return jdbcTemplate.update(sql, filmId, getMPAId(mpa)) > 0;
    }

    private Boolean hasMPA(Integer filmId, MPA mpa) {
        String sql = """
                SELECT COUNT(*) FROM FILM_MPA
                JOIN MPA ON FILM_MPA.MPA_ID = MPA.ID
                WHERE FILM_ID = ? AND TITLE = ?
                """;
        return Optional.ofNullable(
                        jdbcTemplate.queryForObject(sql, Integer.class, filmId, mpa.name()))
                .orElse(0) > 0;
    }

    private Integer getMPAId(MPA mpa) {
        String sql = "SELECT ID FROM MPA WHERE TITLE = ?";
        return Optional.ofNullable(
                        jdbcTemplate.queryForObject(sql, Integer.class, mpa.name()))
                .orElseThrow(() -> new MPANotFoundException("MPA " + mpa + " not found"));
    }
}
