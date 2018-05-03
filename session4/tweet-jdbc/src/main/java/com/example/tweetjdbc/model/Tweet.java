package com.example.tweetjdbc.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import java.util.Objects;

@Validated
public class Tweet {
    private long id;
    @NotNull
    private long owner_id;
    private LocalDateTime created;
    @Size(max = 140)
    private String text;

    public Tweet(long id, String text, long owner_id, LocalDateTime created) {
        this.id = id;
        this.owner_id = owner_id;
        this.created = created;
        this.text = text;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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
        return id == tweet.id &&
                owner_id == tweet.owner_id &&
                Objects.equals(created, tweet.created) &&
                Objects.equals(text, tweet.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner_id, created, text);
    }
}
