package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private final String email;
    @NotBlank(message = "Login cannot be blank")
    @Pattern(regexp = "^[^\\s]+$", message = "Login must not contain spaces")
    private final String login;
    @NotNull(message = "Birthday cannot be null")
    private final LocalDate birthday;
    private Set<Integer> friends;
}
