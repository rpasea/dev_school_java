package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class UserDAOIT {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TweetDAO tweetDAO;
    @Test
    public void shouldCorrectlyRetrieveUser() {
        User expected = new User(1, "gicu_testeanu");
        assertEquals(expected, userDAO.getUser(1L).get());

    }

    @Test
    public void testInsertUser(){
        User expected2 = new User(2, "gicu_testeanu2");
        userDAO.insertUser(expected2);
        assertEquals(expected2, userDAO.getUser(2L).get());
    }

    @Test
    public void testDeleteUser(){
        User expected2 = new User(2, "gicu_testeanu2");
        userDAO.insertUser(expected2);
        userDAO.deleteUser(2L);

        assertEquals(Optional.empty(), userDAO.getUser(2L).get());
    }
    @Test
    public  void  testgettweet(){
        Tweet expect = new Tweet(1, "hello from the test world!", 1, LocalDateTime.now());
        Tweet t = tweetDAO.getTweet(1L).get();
        assertEquals(expect.getId(), t.getId());
        assertEquals(expect.getText(), t.getText());
    }
}
