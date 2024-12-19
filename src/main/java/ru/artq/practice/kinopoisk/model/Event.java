package ru.artq.practice.kinopoisk.model;

import lombok.*;
import ru.artq.practice.kinopoisk.model.enums.EventType;
import ru.artq.practice.kinopoisk.model.enums.Operation;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Event {
    @EqualsAndHashCode.Include
    private Integer eventId;
    private final Timestamp timestamp;
    private final Integer userId;
    private final Integer entityId;
    private final EventType eventType;
    private final Operation operation;
}
