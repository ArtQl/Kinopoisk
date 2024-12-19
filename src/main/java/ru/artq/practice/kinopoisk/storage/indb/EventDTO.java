package ru.artq.practice.kinopoisk.storage.indb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.model.Event;
import ru.artq.practice.kinopoisk.model.enums.EventType;
import ru.artq.practice.kinopoisk.model.enums.Operation;
import ru.artq.practice.kinopoisk.storage.EventStorage;
import ru.artq.practice.kinopoisk.storage.indb.rowmapper.EventRowMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

@Component
@Profile("db")
@RequiredArgsConstructor
public class EventDTO implements EventStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Event> getEventsUser(Integer id) {
        String sql = "SELECT * FROM EVENT WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, new EventRowMapper(), id);
    }

    @Override
    public void addEventToFeed(Integer userId, Integer entityId,
                               EventType eventType, Operation operation) {
        String sql = "INSERT INTO EVENT (TIME_STAMP, USER_ID, EVENT, OPERATION, ENTITY_ID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                Timestamp.from(Instant.now()),
                userId,
                eventType.name(),
                operation.name(),
                entityId
        );
    }
}
