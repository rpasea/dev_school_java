package com.example.tweetjdbc.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tweet {
    private long id;
    private long owner_id;
    private  String text;
    private LocalDateTime created;

    public Tweet(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet1 = (Tweet) o;
        return id == tweet1.id &&
                owner_id == tweet1.owner_id &&
                Objects.equals(text, tweet1.text) &&
                Objects.equals(created, tweet1.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner_id, text, created);
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public long getId() {
        return id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Tweet(long id, long owner_id, String tweet, LocalDateTime created) {
        this.id = id;
        this.owner_id = owner_id;
        this.text = text;
        this.created = created;
    }
}
