package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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

