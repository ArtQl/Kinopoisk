package ru.artq.practice.kinopoisk.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.model.AbstractEntity;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class AbstractEntityStorage<T extends AbstractEntity> {
    protected final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final String tableName;
    private final RowMapper<T> rowMapper;

    public AbstractEntityStorage(JdbcTemplate jdbcTemplate, String tableName, RowMapper<T> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.rowMapper = rowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(tableName)
                .usingGeneratedKeyColumns("ID");
    }

    public T add(T entity) {
        if (entity.getId() != null)
            throw new ValidationException("ID should be null for new entities");
        Number id = simpleJdbcInsert
                .executeAndReturnKey(Map.of("TITLE", entity.getTitle()));
        entity.setId(id.intValue());
        return entity;
    }

    public Boolean remove(T entity) {
        String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
        return jdbcTemplate.update(sql, entity.getId()) > 0;
    }

    public T update(T entity) {
        if (entity.getId() == null)
            throw new ValidationException("ID must be provided for update");
        String sql = "UPDATE " + tableName + " SET TITLE = ? WHERE ID = ?";
        jdbcTemplate.update(sql, entity.getTitle(), entity.getId());
        return entity;
    }

    public Collection<T> getAll() {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public T getById(Integer id) {
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id))
                .orElseThrow(() -> new NoSuchElementException("Entity not found for ID: " + id));
    }
}
