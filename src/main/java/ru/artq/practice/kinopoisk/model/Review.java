package ru.artq.practice.kinopoisk.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Review {
    @EqualsAndHashCode.Include
    private Integer reviewId;
    @NotNull
    private final Integer filmId;
    @NotNull
    private final Integer userId;
    @NotBlank
    private final String content;
    private final Boolean isPositive;
    private Integer useful = 0;
    private Set<Integer> usersLike;
    private Set<Integer> usersDislike;

    public void likeReview(Integer userId) {
        if (usersLike.contains(userId)) return;
        usersDislike.remove(userId);
        ++useful;
        usersLike.add(userId);

    }
    public void dislikeReview(Integer userId) {
        if (usersDislike.contains(userId)) return;
        usersLike.remove(userId);
        --useful;
        usersDislike.add(userId);
    }

    public void removeLike(Integer userId) {
        if (!usersLike.contains(userId)) return;
        --useful;
        usersLike.remove(userId);
    }

    public void removeDislike(Integer userId) {
        if (!usersDislike.contains(userId)) return;
        ++useful;
        usersDislike.remove(userId);
    }
}
