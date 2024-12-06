package ru.artq.practice.kinopoisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre implements AbstractEntity {
    private Integer id;
    private final String title;
}
