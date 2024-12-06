package ru.artq.practice.kinopoisk.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    private Integer eventId;
    private final Long timestamp;
    private final Integer userId;
    private final EventType eventType;
    private final Operation operation;
    private final Integer entityId;

}
