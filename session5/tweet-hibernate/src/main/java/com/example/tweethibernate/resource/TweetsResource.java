package com.example.tweethibernate.resource;

import com.example.tweethibernate.exception.NotFoundException;
import com.example.tweethibernate.model.Tweet;
import com.example.tweethibernate.model.User;
import com.example.tweethibernate.service.TweetService;
import com.example.tweethibernate.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetsResource {
    private TweetService tweetService;
    private UserService userService;

    public TweetsResource(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GetMapping
    public List<Tweet> getTweets(Principal principal) {
        User owner = userService.getUserByName(principal.getName()).orElseThrow(NotFoundException::new);

        return tweetService.getTweetsByOwner(owner);
    }

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Tweet addTweet(@Valid @RequestBody Tweet tweet) {
        tweet.setCreated(LocalDateTime.now());
        return tweetService.insertTweet(tweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
    }

}
