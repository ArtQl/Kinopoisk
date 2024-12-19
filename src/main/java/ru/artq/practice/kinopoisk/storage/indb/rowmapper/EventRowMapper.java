package ru.artq.practice.kinopoisk.storage.indb.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.artq.practice.kinopoisk.model.Event;
import ru.artq.practice.kinopoisk.model.enums.EventType;
import ru.artq.practice.kinopoisk.model.enums.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) {
        try {
            return new Event(
                    rs.getInt("EVENT_ID"),
                    rs.getTimestamp("TIME_STAMP"),
                    rs.getInt("USER_ID"),
                    rs.getInt("ENTITY_ID"),
                    EventType.valueOf(rs.getString("EVENT")),
                    Operation.valueOf(rs.getString("OPERATION"))
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to User object", e);
        }
    }
}
