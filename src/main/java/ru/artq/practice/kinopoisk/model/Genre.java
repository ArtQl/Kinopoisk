package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre implements AbstractEntity {
    private Integer id;
    @NotBlank
    private final String title;
}
