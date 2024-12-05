package ru.artq.practice.kinopoisk.model;

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
    private final Integer filmId;
    private final Integer userId;
    private final String content;
    private final Boolean isPositive;
    private Integer useful = 0;
    private Set<Integer> usersLike;
    private Set<Integer> usersDislike;

    public Boolean likeReview(Integer userId) {
        if (usersLike.contains(userId)) return false;
        usersDislike.remove(userId);
        ++useful;
        return usersLike.add(userId);

    }
    public Boolean dislikeReview(Integer userId) {
        if (usersDislike.contains(userId)) return false;
        usersLike.remove(userId);
        --useful;
        return usersDislike.add(userId);
    }

    public Boolean removeLike(Integer userId) {
        if (!usersLike.contains(userId)) return false;
        --useful;
        return usersLike.remove(userId);
    }

    public Boolean removeDislike(Integer userId) {
        if (!usersDislike.contains(userId)) return false;
        ++useful;
        return usersDislike.remove(userId);
    }
}
