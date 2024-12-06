package ru.artq.practice.kinopoisk.storage.indb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.model.Friendship;
import ru.artq.practice.kinopoisk.model.FriendshipStatus;
import ru.artq.practice.kinopoisk.storage.FriendshipStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Profile("db")
public class FriendshipDTO implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Friendship> rowMapper = ((rs, rowNum) -> new Friendship(
            rs.getInt("USER_ID"),
            rs.getInt("FRIEND_ID"),
            FriendshipStatus.valueOf(rs.getString("STATUS")))
    );

    @Autowired
    public FriendshipDTO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void sendFriendRequest(Integer userId, Integer friendId) {
        if (existsFriendship(userId, friendId) || existsFriendship(friendId, userId)) {
            log.info("Friend request already exists or users are already friends.");
            throw new FriendshipException("Friend request already exists or users are already friends.");
        }
        String sql = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, FriendshipStatus.PENDING.name());
        log.info("Friend request sent from {} to {}", userId, friendId);
    }

    @Override
    public void acceptFriendRequest(int userId, int friendId) {
        String sql = "UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        int rowsUpdated = jdbcTemplate.update(sql, FriendshipStatus.ACCEPTED.name(), userId, friendId);
        if (rowsUpdated == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID and {} ID are now friends!", userId, friendId);
    }

    @Override
    public void rejectFriendRequest(int userId, int friendId) {
        String sql = "UPDATE FRIENDS SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        int rowsDeleted = jdbcTemplate.update(sql, FriendshipStatus.REJECTED.name(), userId, friendId);
        if (rowsDeleted == 0)
            throw new FriendshipException("No pending friend request found.");
        log.info("{} ID request to {} ID was rejected.", userId, friendId);
    }

    @Override
    public Collection<Friendship> getFriendshipsById(Integer userId) {
        String sql = "SELECT * FROM FRIENDS WHERE (USER_ID = ? or FRIEND_ID = ?) AND STATUS = 'ACCEPTED'";
        return jdbcTemplate.query(sql, rowMapper, userId, userId);
    }

    @Override
    public Friendship findFriendship(int userId, int friendId) {
        String sql = "SELECT * FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        List<Friendship> f = jdbcTemplate.query(sql, rowMapper, userId, friendId);
        if (f.isEmpty()) throw new FriendshipException("Friendship not found");
        return f.getFirst();
    }

    @Override
    public void clear() {
        String sql = "SELECT COUNT(*) as res FROM FRIENDS";
        Integer res = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("res"));
        if (res != null && res > 0)
            jdbcTemplate.execute("DELETE FROM FRIENDS WHERE USER_ID > 0");
    }

    @Override
    public Collection<Integer> getCommonFriends(Integer userId, Integer otherUserId) {
        String sql = """
                SELECT USER_ID ID FROM FRIENDS
                WHERE FRIEND_ID = ?
                  AND STATUS = 'ACCEPTED'
                UNION ALL
                SELECT FRIEND_ID ID FROM FRIENDS
                WHERE USER_ID = ?
                  AND STATUS = 'ACCEPTED'
                """;
        List<Integer> set1 = jdbcTemplate
                .queryForList(sql, Integer.class, userId, userId);
        List<Integer> set2 = jdbcTemplate
                .queryForList(sql, Integer.class, otherUserId, otherUserId);
        set1.retainAll(set2);
        return set1;
    }

    private Boolean existsFriendship(Integer userID, Integer friendId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?",
                        Integer.class, userID, friendId))
                .orElse(0) > 0;
    }
}
