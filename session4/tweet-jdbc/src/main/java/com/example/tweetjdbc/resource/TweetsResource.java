package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.exception.NotFoundException;
import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.repository.TweetDAO;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetsResource {
    private TweetDAO tweetDAO;

    public TweetsResource(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @GetMapping
    public List<Tweet> getTweets() {
        return tweetDAO.getTweets();
    }

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable Long id) {
        return tweetDAO.getTweet(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Tweet addTweet(@RequestBody Tweet user) {
        return tweetDAO.insertTweet(user);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
    }

    }
