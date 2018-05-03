package com.example.tweetjdbc.resource;

import com.example.tweetjdbc.exception.NotFoundException;
import com.example.tweetjdbc.model.Tweet;
import com.example.tweetjdbc.repository.TweetDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")

public class TweetsResource {
    private TweetDAO tweetDAO;

    public TweetsResource(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @GetMapping
    public List<Tweet> getTweets(){return tweetDAO.getTweets();}

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable long id){return tweetDAO.getTweet(id).orElseThrow(NotFoundException::new);}

    @GetMapping("/{owner_id}")
    public List<Tweet> getTweetsByOwner(@PathVariable String owner) {return tweetDAO.getTweetsByOwner(owner);}

    @PostMapping
    public Tweet addNewTweet(@RequestBody Tweet tweet){return tweetDAO.insertTweet(tweet);}

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id){tweetDAO.deleteTweet(id);}
}
