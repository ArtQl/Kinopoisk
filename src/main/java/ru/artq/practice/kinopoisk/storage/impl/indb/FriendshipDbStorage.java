package ru.artq.practice.kinopoisk.storage.impl.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component("inDbFriendshipStorage")
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Friendship> rowMapper = ((rs, rowNum) -> new Friendship(
            rs.getInt("USER_ID"),
            rs.getInt("FRIEND_ID"),
            FriendshipStatus.valueOf(rs.getString("STATUS")))
    );

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean sendFriendRequest(Integer userId, Integer friendId) {
        if (existsFriendship(userId, friendId) || existsFriendship(friendId, userId)) {
            throw new FriendshipException("Friend request already exists or users are already friends.");
        }
        String sql = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, FriendshipStatus.PENDING);
        log.info("Friend request sent from {} to {}", userId, friendId);
        return true;
    }

    @Override
    public Boolean acceptFriendRequest(int userId, int friendId) {
        String sql = "UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        int rowsUpdated = jdbcTemplate.update(sql, FriendshipStatus.ACCEPTED, userId, friendId);
        if (rowsUpdated == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID and {} ID are now friends!", userId, friendId);
        return true;
    }

    @Override
    public Boolean rejectFriendRequest(int userId, int friendId) {
        String sql = "UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        int rowsDeleted = jdbcTemplate.update(sql, FriendshipStatus.REJECTED, userId, friendId);
        if (rowsDeleted == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID request to {} ID was rejected.", userId, friendId);
        return true;
    }

    @Override
    public Collection<Friendship> getFriendshipsById(Integer userId) {
        String sql = "SELECT * FROM FRIENDS WHERE (USER_ID = ? or FRIEND_ID = ?) AND STATUS = ?";
        return jdbcTemplate.query(sql, rowMapper, FriendshipStatus.ACCEPTED, userId, userId);
    }

    @Override
    public Friendship findFriendship(int userId, int friendId) {
        String sql = "SELECT * FROM FRIENDS WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)";
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(sql, rowMapper, userId, friendId, friendId, userId))
                .orElseThrow(() -> new FriendshipException("Friendship not found"));
    }

    @Override
    public Collection<Integer> getCommonFriends(Integer userId, Integer otherUserId) {
        String sql = """
                SELECT f1.friend_id FROM friends f1
                JOIN friends f2 ON f1.friend_id = f2.friend_id
                WHERE f1.user_id = ? AND f2.user_id = ?
                AND f1.status = ? AND f2.status = ?
                """;
        return jdbcTemplate.queryForList(sql, Integer.class,
                userId, otherUserId, FriendshipStatus.ACCEPTED, FriendshipStatus.ACCEPTED);
    }

    private Boolean existsFriendship(Integer userID, Integer friendId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?",
                Integer.class, userID, friendId))
                .orElse(0) > 0;
    }
}
