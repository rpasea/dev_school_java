package com.example.tweetjdbc.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tweet {
    // TODO

    private long          id;
    private long          owner_id;
    private LocalDateTime created;
    private String        text;


    public Tweet(long id, String text, LocalDateTime created, long owner_id) {
        this.id = id;
        this.text = text;
        this.created = created;
        this.owner_id = owner_id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {

        return id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return id == tweet.id &&
                owner_id == tweet.owner_id &&
                Objects.equals(created, tweet.created) &&
                Objects.equals(text, tweet.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner_id, created, text);
    }

    public LocalDateTime getCreated() {
        return created;

    }

    public String getText() {
        return text;
    }


}
