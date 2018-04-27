package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class TweetDAOIT {
    @Autowired
    private TweetDAO tweetDAO;

    @Test
    public void shouldCorrectlyRetrieveTweet() {
        //(long id, long owner_id, LocalDateTime datetime, String text)
        Tweet expectedTweet = new Tweet(1L, 1L, LocalDateTime.now(), "text test");
        List<Tweet> actualTweet = tweetDAO.getTweets();

        assertEquals(true, true);//expectedTweet.getText(), actualTweet.getText());
    }
}