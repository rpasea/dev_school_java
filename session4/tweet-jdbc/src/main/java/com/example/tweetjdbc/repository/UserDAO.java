package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    private static class UserMapper implements RowMapper<User> {

        @Nullable
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

    private JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * from user", new UserDAO.UserMapper());
    }

    public Optional<User> getUser(Long id) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * from user where id = ?",
                            new Object[]{id},
                            new UserDAO.UserMapper()));
        } catch (DataAccessException ex) {
            LOG.info("Entity not found");
            return Optional.empty();
        }
    }

    public User insertUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user")
                .usingGeneratedKeyColumns("id");
        Number id = insert.executeAndReturnKey(params);
        return getUser((long)id).get();
    }

    public void deleteUser(Long id) {
        jdbcTemplate.update("DELETE from user where id = ?", new Object[] {id});
    }
}
