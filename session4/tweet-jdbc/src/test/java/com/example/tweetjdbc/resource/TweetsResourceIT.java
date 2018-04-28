package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.repository.TweetDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetsResource.class)
public class TweetsResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetDAO tweetDAO;

    @Test
    public void shouldRetrieveTweets() throws Exception {
        // you should also check the result itself, not only the code
        mockMvc.perform(
                get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is(200));
    }

}
