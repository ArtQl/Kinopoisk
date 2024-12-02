package ru.artq.practice.kinopoisk.model;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Review {
    @EqualsAndHashCode.Include
    private Integer id;
    private final Integer filmId;
    private final Integer userId;
    private final String review;
}
