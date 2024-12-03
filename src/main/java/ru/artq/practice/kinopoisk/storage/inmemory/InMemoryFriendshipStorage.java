package ru.artq.practice.kinopoisk.storage.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile("in-memory")
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final Map<Integer, Set<Friendship>> friendships = new HashMap<>();

    @Override
    public Boolean sendFriendRequest(Integer userId, Integer friendId) {
        Friendship fUser = new Friendship(userId, friendId);
        friendships.putIfAbsent(userId, new HashSet<>());
        friendships.putIfAbsent(friendId, new HashSet<>());

        if (existsFriendship(userId, friendId)
                || existsFriendship(friendId, userId)) {
            log.info("Users are already friends or have a pending request");
            return false;
        }
        friendships.get(userId).add(fUser);
        friendships.get(friendId).add(fUser);
        log.info("Friend request sent from {} to {}", userId, friendId);
        return true;
    }

    @Override
    public Boolean acceptFriendRequest(int userId, int friendId) {
        Friendship friendship = findFriendship(userId, friendId);
        if (friendship.getStatus() != FriendshipStatus.PENDING)
            throw new IllegalArgumentException("No pending friend request found.");
        friendship.accept();
        log.info("{} ID and {} ID are now friends!", friendship.getUserId(), friendship.getFriendId());
        return true;
    }

    @Override
    public Boolean rejectFriendRequest(int userId, int friendId) {
        Friendship friendship = findFriendship(userId, friendId);
        if (friendship.getStatus() != FriendshipStatus.PENDING)
            throw new IllegalArgumentException("No pending friend request found.");
        friendship.reject();
        log.info("{} ID request to {} ID was rejected.", friendship.getUserId(), friendship.getFriendId());
        return true;
    }

    @Override
    public Collection<Friendship> getFriendshipsById(Integer userId) {
        return friendships.getOrDefault(userId, Set.of()).stream()
                .filter(f -> f.getStatus() == FriendshipStatus.ACCEPTED)
                .toList();
    }

    @Override
    public Friendship findFriendship(int userId, int friendId) {
        return friendships.getOrDefault(userId, Set.of())
                .stream()
                .filter(f -> f.getFriendId().equals(friendId))
                .findFirst().orElseThrow(() -> new FriendshipException("Friendship not found"));
    }

    @Override
    public Collection<Integer> getCommonFriends(Integer userId, Integer otherUserId) {
        Set<Integer> userSet = getFriendshipsById(userId).stream()
                .map(f -> f.getUserId().equals(userId)
                        ? f.getFriendId() : f.getUserId())
                .collect(Collectors.toSet());
        Set<Integer> otherUserSet = getFriendshipsById(otherUserId).stream()
                .map(f -> f.getUserId().equals(otherUserId)
                        ? f.getFriendId() : f.getUserId())
                .collect(Collectors.toSet());
        userSet.retainAll(otherUserSet);
        return userSet;
    }

    private boolean existsFriendship(Integer userID, Integer friendId) {
        return friendships.getOrDefault(userID, Set.of()).stream()
                .anyMatch(f -> f.getFriendId().equals(friendId));
    }
}