package ru.artq.practice.kinopoisk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Film {
    @EqualsAndHashCode.Include
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private final String name;
    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private final String description;
    @NotNull(message = "Release date cannot be null")
    private final LocalDate releaseDate;
    @NotNull(message = "Duration cannot be null")
    private final Duration duration;
    private Set<Integer> genres = new HashSet<>();
    private Set<Integer> mpa = new HashSet<>();
    private Set<Integer> directors = new HashSet<>();

    @JsonCreator
    public Film(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration") Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}



