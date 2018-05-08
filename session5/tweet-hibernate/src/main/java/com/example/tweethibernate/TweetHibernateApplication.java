package com.example.tweethibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class TweetHibernateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetHibernateApplication.class, args);
    }
}
