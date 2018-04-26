package com.example.tweetjdbc.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

public class Tweet {
    private long id;
    private long owner_id;
    private LocalDateTime created;
    private String text;

    public Tweet() {
    }

    public Tweet(long id, String text, LocalDateTime created, long owner_id) {
        this.id = id;
        this.owner_id = owner_id;
        this.created = created;
        this.text = text;
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
}
