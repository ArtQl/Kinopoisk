package ru.artq.practice.kinopoisk.storage;

import ru.artq.practice.kinopoisk.model.Event;
import ru.artq.practice.kinopoisk.model.enums.EventType;
import ru.artq.practice.kinopoisk.model.enums.Operation;

import java.util.Collection;

public interface EventStorage {

    Collection<Event> getEventsUser(Integer id);

    void addEventToFeed(Integer userId, Integer entityId,
                        EventType eventType, Operation operation);
}
