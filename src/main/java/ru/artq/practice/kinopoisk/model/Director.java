package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Director {
    @EqualsAndHashCode.Include
    private Integer id;
    @NotNull(message = "Name cannot be blank")
    private final String name;
    @NotNull(message = "Birthday cannot be null")
    private final LocalDate birthday;
}
