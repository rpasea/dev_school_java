package com.example.tweethibernate.repository;

import com.example.tweethibernate.model.Tweet;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TweetDAO.class);

    public List<Tweet> getTweets(Session session) {
        CriteriaQuery<Tweet> query = session.getCriteriaBuilder().createQuery(Tweet.class);
        query.from(Tweet.class);
        return session.createQuery(query).list();
    }

    public List<Tweet> getTweetsByOwner(Long ownerId, Session session) {
        // TODO
        return null;
    }

    public Optional<Tweet> getTweet(Long id, Session session) {
        // TODO
        return null;
    }

    public Tweet insertTweet(Tweet tweet, Session session) {
        // TODO
        return null;
    }

    public void deleteTweet(Tweet tweet, Session session) {
        // TODO
    }
}
