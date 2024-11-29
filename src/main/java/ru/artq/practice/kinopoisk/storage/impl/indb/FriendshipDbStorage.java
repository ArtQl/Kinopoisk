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
import java.util.List;

@Slf4j
@Component("friendshipDbStorage")
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

        jdbcTemplate.update(
                "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, ?)",
                userId, friendId, FriendshipStatus.PENDING);
        log.info("Friend request sent from {} to {}", userId, friendId);
        return true;
    }

    @Override
    public void acceptFriendRequest(int userId, int friendId) {
        int rowsUpdated = jdbcTemplate.update("UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?",
                FriendshipStatus.ACCEPTED, userId, friendId);
        if (rowsUpdated == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID and {} ID are now friends!", userId, friendId);
    }

    @Override
    public void rejectFriendRequest(int userId, int friendId) {
        // "DELETE FROM FRIENDS WHERE STATUS = ? AND USER_ID = ? AND FRIEND_ID = ?"
        int rowsDeleted = jdbcTemplate.update("UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?",
                FriendshipStatus.REJECTED, userId, friendId);
        if (rowsDeleted == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID request to {} ID was rejected.", userId, friendId);
    }

    @Override
    public Collection<Friendship> getFriendshipsById(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM FRIENDS WHERE (USER_ID = ? or FRIEND_ID = ?) AND STATUS = ?",
                rowMapper,
                FriendshipStatus.ACCEPTED, userId, userId);
    }

    @Override
    public Friendship findFriendship(int userId, int friendId) {
        Friendship friendship = jdbcTemplate
                .queryForObject("SELECT * FROM FRIENDS WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)",
                        rowMapper,
                        userId, friendId, friendId, userId);
        if (friendship == null)
            throw new FriendshipException("Friendship not found");
        return friendship;
    }

    @Override
    public List<Integer> getCommonFriends(Integer userId, Integer otherUserId) {
        String sql = """
                SELECT f1.friend_id
                FROM friends f1
                JOIN friends f2 ON f1.friend_id = f2.friend_id
                WHERE f1.user_id = ? AND f2.user_id = ?
                AND f1.status = ? AND f2.status = ?
                """;
        return jdbcTemplate.queryForList(sql, Integer.class, userId, otherUserId, FriendshipStatus.ACCEPTED, FriendshipStatus.ACCEPTED);
    }

    private boolean existsFriendship(Integer userID, Integer friendId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?",
                Integer.class,
                userID, friendId);
        return count != null && count > 0;
    }
}
