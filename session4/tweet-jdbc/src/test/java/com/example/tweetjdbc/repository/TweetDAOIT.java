package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class TweetDAOIT {
    @Autowired
    private TweetDAO tweetDAO;

    @Test
    public void shouldCorrectlyRetrieveTweet() {
        Tweet expected = new Tweet(1, 1, LocalDateTime.now(), "hello from the test world!");
        Tweet returned = tweetDAO.getTweet(1L).get();

        assertEquals(expected.getId(), returned.getId());
        assertEquals(expected.getOwner_id(), returned.getOwner_id());
        assertEquals(expected.getText(), returned.getText());
    }
}
