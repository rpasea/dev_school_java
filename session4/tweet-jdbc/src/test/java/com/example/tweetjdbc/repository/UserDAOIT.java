package com.example.tweetjdbc.repository;

import com.example.tweetjdbc.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDbConfiguration.class)
public class UserDAOIT {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldCorrectlyRetrieveUser() {
        User expected = new User(1, "gicu_testeanu");
        User actualUser = userDAO.getUser(1L).get();
        assertEquals(expected, actualUser);
    }
}
