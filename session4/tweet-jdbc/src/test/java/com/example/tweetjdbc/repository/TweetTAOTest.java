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
public class TweetTAOTest {
    @Autowired
    private TweetDAO tweetDAO;

    @Test
    public void shouldCorrectlyRetrieveTweet() {
        Tweet expected = new Tweet(1L, "hello from the test world!", 1L, LocalDateTime.now());

        assertEquals(expected, tweetDAO.getTweet(1L).get());
    }
}
