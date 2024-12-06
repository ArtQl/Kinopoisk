package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.model.AbstractEntity;
import ru.artq.practice.kinopoisk.service.AbstractService;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class AbstractController<T extends AbstractEntity> {
    private final AbstractService<T> abstractService;

    @GetMapping
    public Collection<T> getAll() {
        return abstractService.getAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable Integer id) {
        return abstractService.getById(id);
    }

    @PostMapping
    public T add(@RequestBody T entity) {
        return abstractService.add(entity);
    }

    @PutMapping
    public T update(@RequestBody T entity) {
        return abstractService.update(entity);
    }

    @DeleteMapping
    public Boolean remove(@RequestBody T entity) {
        return abstractService.remove(entity);
    }
}
