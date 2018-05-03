package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.repository.TweetDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetsResource.class)
public class TweetsResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TweetDAO tweetDAO;

    @Test
    public void shouldRetrieveTweets() throws Exception {
        // you should also check the result itself, not only the code
        List<Tweet> tweets =Arrays.asList(new Tweet
                        (1, "New tweet",1,LocalDateTime.now()));

        Mockito.when(tweetDAO.getTweets()).thenReturn(tweets);
        mockMvc.perform(

                get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().json(objectMapper.writeValueAsString(tweets)))
                .andDo(print()).andExpect(status().is(200));
    }

}
