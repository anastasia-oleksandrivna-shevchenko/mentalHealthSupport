package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Timestamp created_at;
    private boolean is_anonymous;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    public String getFormattedCreatedAt() {
        return created_at.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    // Геттер та сетер для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Геттер та сетер для content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    // Геттер та сетер для is_anonymous
    public boolean isIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    // Геттер та сетер для post
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    // Геттер та сетер для user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}