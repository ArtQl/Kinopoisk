package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    @EqualsAndHashCode.Include
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private final String name;
    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private final String description;
    @NotNull(message = "Release date cannot be null")
    @Future(groups = LocalDate.class, message = "Release date cannot be in the future")
    private final LocalDate releaseDate;
    @NotNull(message = "Duration cannot be null")
    private final Duration duration;
    private Set<Integer> genres;
    private Set<Integer> mpa;
    private Set<Integer> directors;
}
