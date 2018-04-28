package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TweetDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TweetDAO.class);

    private static class TweetMapper implements RowMapper<Tweet> {

        @Nullable
        @Override
        public Tweet mapRow(ResultSet resultSet, int i) throws SQLException {
            // map from a ResultSet to a Tweet

            return new Tweet(resultSet.getLong("id"), resultSet.getLong("owner_id"),
                    resultSet.getString("text"), resultSet.getTimestamp("created").toLocalDateTime());
        }
    }

    private JdbcTemplate jdbcTemplate;

    public TweetDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tweet> getTweets() {
        return jdbcTemplate.query("SELECT * from tweet", new TweetDAO.TweetMapper());
    }

    public List<Tweet> getTweetsByOwner(String owner) {
        // write your query
        return null;
    }

    public Optional<Tweet> getTweet(Long id) {
        // write your query
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * from tweet where id = ?",
                            new Object[]{id},
                            new TweetDAO.TweetMapper()));
        } catch (DataAccessException ex) {
            LOG.error("Entity not found");
            return Optional.empty();
        }
    }

    public Tweet insertTweet(Tweet tweet) {
        // write your query

        // use the SimpleJdbcInsert class
        return null;
    }

    public void deleteTweet(Long id) {
        // write your query
    }
}
