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
            return new Tweet(resultSet.getLong("id"),resultSet.getString("text"),resultSet.getLong("owner_id"),
                    resultSet.getTimestamp("created").toLocalDateTime()
                    );

        }
    }

    private JdbcTemplate jdbcTemplate;

    public TweetDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tweet> getTweets() {
        // write your query
        return jdbcTemplate.query("SELECT * FROM tweets", new TweetDAO.TweetMapper());
    }

    public List<Tweet> getTweetsByOwner(String owner) {
        return jdbcTemplate.query("SELECT * FROM tweets where owner_id = ?",
                new Object[] {owner}, new TweetDAO.TweetMapper());

    }

    public Optional<Tweet> getTweet(Long id) {
        // write your query

        try {


            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM tweets where id = ?",
                            new Object[]{id}, new TweetMapper()));
        }catch (DataAccessException ex) {

            LOG.info("Not found");
            // how about not found?
            return Optional.empty();
        }
    }

    public Tweet insertTweet(Tweet tweet) {
        // write your query
        Map<String, Object> newTweet = new HashMap<>();
        newTweet.put("text", tweet.getText());
        newTweet.put("owner_id", tweet.getOwner_id());
        newTweet.put("created", tweet.getCreated());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tweet").usingGeneratedKeyColumns("id");
        Number id = insert.executeAndReturnKey(newTweet);
        return getTweet((long)id).get();

        // use the SimpleJdbcInsert class

    }

    public void deleteTweet(Long id) {
        jdbcTemplate.update("DELETE from tweet where id = ?", new Object[] {id});
        // write your query
    }
}
