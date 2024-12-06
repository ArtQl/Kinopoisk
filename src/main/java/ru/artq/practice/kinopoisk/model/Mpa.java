package ru.artq.practice.kinopoisk.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mpa implements AbstractEntity {
    private Integer id;
    private final String title;
}
