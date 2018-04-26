package com.example.tweetjdbc.repository;


import com.example.tweetjdbc.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class TweetDAOIT {

   @Autowired
   private TweetDAO tweetDAO;

   @Test
   public void shouldCorrectlyRetrieveUser() {
      Tweet expected = new Tweet(1, "hello from the test world!", LocalDateTime.now(), 1L );
       Tweet found = tweetDAO.getTweet(1L).get();
       assertEquals(found.getOwner_id(), expected.getOwner_id());
       assertEquals(found.getText(), expected.getText());
   }
}
