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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@RunWith(SpringRunner.class)
@WebMvcTest(TweetsResource.class)
public class TweetsResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetDAO tweetDAO;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldRetrieveTweets() throws Exception {
        // you should also check the result itself, not only the code
        List<Tweet> tweets = Arrays.asList(new Tweet(12, "text", 12, LocalDateTime.now()));



        Mockito.when(tweetDAO.getTweets()).thenReturn(tweets);
        mockMvc.perform(
                get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is(200))
        .andExpect(content().json(mapper.writeValueAsString(tweets)));
    }

    @Test
    public void shouldRetrieveTweetsContent() throws Exception {
        // you should also check the result itself, not only the code
        Integer id = 1;
        mockMvc.perform(
                get("/tweets/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(id));
        //contentType("application/json;charset=UTF-8"))
        //        .andExpect(content().string("[" +
        //                "    {" +
        //                "        \"id\": 1," +
        //                "        \"owner_id\": 1," +
        //                "        \"text\": \"hello from the jdbc world!\"," +
        //                "        \"created\": \"2018-04-29T12:55:45.01\"" +
        //                "    }" +
        //                "]"));
    }

}
