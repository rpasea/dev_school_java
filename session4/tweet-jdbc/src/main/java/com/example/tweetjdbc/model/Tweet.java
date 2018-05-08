package com.example.tweetjdbc.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tweet {
    // TODO
    private long id;
    private String text;
    private long owner_id;
    private LocalDateTime created;

    public Tweet(long id, String text, long owner_id, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.owner_id = owner_id;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (id != tweet.id) return false;
        if (owner_id != tweet.owner_id) return false;
        if (!text.equals(tweet.text)) return false;
        return created.equals(tweet.created);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + text.hashCode();
        result = 31 * result + (int) (owner_id ^ (owner_id >>> 32));
        result = 31 * result + created.hashCode();
        return result;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime createdTime) {
        this.created = createdTime;
    }

}