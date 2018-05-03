package com.example.tweethibernate.service;

import com.example.tweethibernate.model.Tweet;
import com.example.tweethibernate.model.User;
import com.example.tweethibernate.repository.ProgrammaticTransactionManager;
import com.example.tweethibernate.repository.TweetDAO;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {
    private TweetDAO tweetDAO;
    private ProgrammaticTransactionManager transactionManager;

    public TweetService(TweetDAO tweetDAO, ProgrammaticTransactionManager transactionManager) {
        this.tweetDAO = tweetDAO;
        this.transactionManager = transactionManager;
    }

    public List<Tweet> getTweets() {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<List<Tweet>>() {
            @Override
            public List<Tweet> execute(Session session) {
                return tweetDAO.getTweets(session);
            }
        });
    }

    public List<Tweet> getTweetsByOwner(User owner) {
        // TODO
        return null;
    }

    public Optional<Tweet> getTweet(Long id) {
        // TODO
        return null;
    }

    public Tweet insertTweet(Tweet tweet) {
        // TODO
        return null;
    }

    public void deleteTweet(Long tweetId) {
        // TODO
    }
}
