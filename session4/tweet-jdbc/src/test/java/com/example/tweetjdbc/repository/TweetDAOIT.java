package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class TweetDAOIT {
    @Autowired
    private TweetDAO tweetDAO;

    @Test
    public void shouldCorrectlyRetrieveTweet() {
        Tweet expected = new Tweet(1, "hello from the test world!", 1, LocalDateTime.now() );
        Tweet actual = tweetDAO.getTweet(1L).get();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOwner_id(), actual.getOwner_id());
        assertEquals(expected.getText(), actual.getText());
    }
}
