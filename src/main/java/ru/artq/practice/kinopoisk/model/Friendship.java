package ru.artq.practice.kinopoisk.model;

import lombok.Data;

@Data
public class Friendship {
    private final Integer userId;
    private final Integer friendId;
    private final Boolean isConfirmed;
}
