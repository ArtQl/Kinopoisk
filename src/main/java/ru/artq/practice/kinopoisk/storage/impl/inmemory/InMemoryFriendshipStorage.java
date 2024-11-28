package ru.artq.practice.kinopoisk.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component("inMemoryFriendshipStorage")
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final Set<Friendship> friendships = new HashSet<>();

    @Override
    public Boolean sendFriendRequest(Integer userId, Integer friendId) {
        if (friendships.contains(new Friendship(userId, friendId)) ||
                friendships.contains(new Friendship(friendId, userId))) {
            log.info("Users are already friends or have a pending request");
            return false;
        }
        friendships.add(new Friendship(userId, friendId));
        log.info("Friend request sent from {} to {}", userId, friendId);
        return true;
    }

    @Override
    public void acceptFriendRequest(int userId, int friendId) {
        Friendship friendship = findFriendship(userId, friendId);
        if (friendship.getUserId() == userId && friendship.getFriendId() == friendId)
            throw new IllegalArgumentException("id's friendship is not correct");
        if (!friendship.getStatus().equals(FriendshipStatus.PENDING))
            throw new IllegalArgumentException("No pending friend request found.");
        friendship.accept();
        log.info("{} ID and {} ID are now friends!", friendship.getUserId(), friendship.getFriendId());
    }

    @Override
    public void rejectFriendRequest(int userId, int friendId) {
        Friendship friendship = findFriendship(userId, friendId);
        if (friendship.getUserId() == userId && friendship.getFriendId() == friendId)
            throw new IllegalArgumentException("id's friendship is not correct");
        if (!friendship.getStatus().equals(FriendshipStatus.PENDING))
            throw new IllegalArgumentException("No pending friend request found.");
        friendship.reject();
        log.info("{} ID request to {} ID was rejected.", friendship.getUserId(), friendship.getFriendId());
    }

    @Override
    public Collection<Friendship> getFriendshipsById(Integer userId) {
        return friendships.stream()
                .filter(f -> f.getStatus().equals(FriendshipStatus.ACCEPTED)
                        && (f.getUserId().equals(userId) || f.getFriendId().equals(userId)))
                .toList();
    }

    @Override
    public Friendship findFriendship(int userId, int friendId) {
        Friendship friendship = friendships.stream()
                .filter(f -> (f.getUserId() == userId && f.getFriendId() == friendId)
                        || (f.getUserId() == friendId && f.getFriendId() == userId))
                .findFirst().orElse(null);
        if (friendship == null)
            throw new FriendshipException("Friendship not found");
        return friendship;
    }

    @Override
    public List<Integer> getCommonFriends(Integer userId, Integer otherUserId) {
        Set<Integer> userSet = new HashSet<>();
        Set<Integer> otherUserSet = new HashSet<>();
        if (!getFriendshipsById(userId).isEmpty()) {
            userSet.addAll(
                    getFriendshipsById(userId).stream()
                            .map(f -> f.getUserId().equals(userId)
                                    ? f.getFriendId() : f.getUserId())
                            .toList());
        }
        if (!getFriendshipsById(otherUserId).isEmpty()) {
            otherUserSet.addAll(getFriendshipsById(otherUserId).stream()
                    .map(f -> f.getUserId().equals(otherUserId)
                            ? f.getFriendId() : f.getUserId())
                    .toList());
        }
        if (userSet.isEmpty() || otherUserSet.isEmpty()) return List.of();
        userSet.retainAll(otherUserSet);
        return userSet.stream().toList();
    }
}
