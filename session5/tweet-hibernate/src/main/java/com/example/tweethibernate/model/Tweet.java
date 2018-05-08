package com.example.tweethibernate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Size(max = 140)
    private String text;
    private LocalDateTime created;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Tweet() {
    }

    public Tweet(Long id, String text, LocalDateTime created, User owner) {
        this.id = id;
        this.text = text;
        this.created = created;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwnerId(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (id != null ? !id.equals(tweet.id) : tweet.id != null) return false;
        if (text != null ? !text.equals(tweet.text) : tweet.text != null) return false;
        if (created != null ? !created.equals(tweet.created) : tweet.created != null) return false;
        return owner != null ? owner.equals(tweet.owner) : tweet.owner == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
