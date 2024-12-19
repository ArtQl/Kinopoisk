package ru.artq.practice.kinopoisk.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Director implements AbstractEntity {
    private Integer id;
    private final String name;

    @Override
    public String getTitle() {
        return name;
    }
}

