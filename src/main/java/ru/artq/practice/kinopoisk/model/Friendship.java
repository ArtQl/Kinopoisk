package ru.artq.practice.kinopoisk.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.artq.practice.kinopoisk.model.enums.FriendshipStatus;

@Getter
@Setter
@ToString
public class Friendship {
    @EqualsAndHashCode.Include
    private final Integer userId;
    @EqualsAndHashCode.Include
    private final Integer friendId;
    private FriendshipStatus status = FriendshipStatus.PENDING;

    public Friendship(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Friendship(Integer userId, Integer friendId, FriendshipStatus status) {
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
    }

    public void accept() {
        this.status = FriendshipStatus.ACCEPTED;
    }

    public void reject() {
        this.status = FriendshipStatus.REJECTED;
    }
}
