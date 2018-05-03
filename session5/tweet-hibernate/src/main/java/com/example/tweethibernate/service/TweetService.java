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
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<List<Tweet>>() {
            @Override
            public List<Tweet> execute(Session session) {
                return tweetDAO.getTweetsByOwner(owner.getId(), session);
            }
        });
    }

    public Optional<Tweet> getTweet(Long id) {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Optional<Tweet>>() {
            @Override
            public Optional<Tweet> execute(Session session) {
                return tweetDAO.getTweet(id, session);
            }
        });
    }

    public Tweet insertTweet(Tweet tweet) {
        return transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Tweet>() {
            @Override
            public Tweet execute(Session session) {
                return tweetDAO.insertTweet(tweet, session);
            }
        });
    }

    public void deleteTweet(Long tweetId) {
        transactionManager.doInTransaction(new ProgrammaticTransactionManager.BusinessTransaction<Void>() {
            @Override
            public Void execute(Session session) {
                Optional<Tweet> tweet = tweetDAO.getTweet(tweetId, session);
                if (tweet.isPresent()) {
                    tweetDAO.deleteTweet(tweet.get(), session);
                }
                return null;
            }
        });
    }
}
