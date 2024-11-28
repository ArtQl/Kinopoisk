package ru.artq.practice.kinopoisk.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Friendship {
    private final Integer userId;
    private final Integer friendId;
    private FriendshipStatus status = FriendshipStatus.PENDING;

    public Friendship(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public void accept() {
        this.status = FriendshipStatus.ACCEPTED;
    }

    public void reject() {
        this.status = FriendshipStatus.REJECTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(userId, that.userId) && Objects.equals(friendId, that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}
