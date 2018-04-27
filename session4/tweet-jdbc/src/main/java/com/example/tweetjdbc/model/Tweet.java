package com.example.tweetjdbc.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tweet {
    private long id;
    private long owner_id;
    private LocalDateTime created;
    private String text;

    public Tweet() {

    }

    public Tweet(long id, long owner_id, LocalDateTime datetime, String text) {
        this.id = id;
        this.owner_id = owner_id;
        this.created = datetime == null ? LocalDateTime.now() : datetime;
        this.text = text;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Tweet tweet = (Tweet) object;
        return id == tweet.id &&
                owner_id == tweet.owner_id &&
                java.util.Objects.equals(created, tweet.created) &&
                java.util.Objects.equals(text, tweet.text);
    }

    public int hashCode() {

        return Objects.hash(super.hashCode(), id, owner_id, created, text);
    }
}
