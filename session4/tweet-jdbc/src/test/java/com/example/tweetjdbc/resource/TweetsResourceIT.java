package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.repository.TweetDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetsResource.class)
public class TweetsResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper serializer;

    @MockBean
    private TweetDAO tweetDAO;

    @Test
    public void shouldRetrieveTweets() throws Exception {
        // you should also check the result itself, not only the code
        List<Tweet> fakeTweets = new ArrayList<>();
        fakeTweets.add(new Tweet(1, 1, LocalDateTime.now(), "fakeTweet_1"));
        fakeTweets.add(new Tweet(2, 1, LocalDateTime.now(), "fakeTweet_2"));
        fakeTweets.add(new Tweet(3, 2, LocalDateTime.now(), "fakeTweet_newUser"));

        given(tweetDAO.getTweets()).willReturn(fakeTweets);

        mockMvc.perform(
                get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json(serializer.writeValueAsString(fakeTweets)));
    }

}
