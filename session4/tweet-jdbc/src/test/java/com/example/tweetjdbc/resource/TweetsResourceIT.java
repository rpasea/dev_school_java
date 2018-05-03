package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.repository.TweetDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetsResource.class)
public class TweetsResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetDAO tweetDAO;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Tweet> tweetList = Collections.singletonList(new Tweet(1l, "tweet body",LocalDateTime.now() ,1l));

    @Before
    public void setup() {
        when(tweetDAO.getTweets()).thenReturn(tweetList);
    }

    @Test
    public void shouldRetrieveTweets() throws Exception {
        // you should also check the result itself, not only the code
        mockMvc.perform(
                get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is(200))
                .andExpect(
                        content().json(objectMapper.writeValueAsString(tweetList))
                );
    }

}
