package ru.artq.practice.kinopoisk.service;

import lombok.RequiredArgsConstructor;
import ru.artq.practice.kinopoisk.model.AbstractEntity;
import ru.artq.practice.kinopoisk.storage.AbstractEntityStorage;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class AbstractService<T extends AbstractEntity> {
    protected final AbstractEntityStorage<T> storage;

    public Collection<T> getAll() {
        return storage.getAll();
    }

    public T add(T entity) {
        return storage.add(entity);
    }

    public Boolean remove(T entity) {
        return storage.remove(entity);
    }

    public T update(T entity) {
        return storage.update(entity);
    }

    public T getById(Integer id) {
        return storage.getById(id);
    }
}
