package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime created_at;
    private boolean is_anonymous;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Конструктори
    /*public Comment() {
        this.replies = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.edited = false;
    }

    public Comment(Long id, String content, User author, Post post) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.post = post;
        this.replies = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.likeCount = 0;
        this.helpfulCount = 0;
        this.edited = false;
    }

    public Comment(Long id, String content, User author, Post post, Comment parent) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.post = post;
        this.parent = parent;
        this.replies = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.likeCount = 0;
        this.helpfulCount = 0;
        this.edited = false;
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
        this.edited = true;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    // Методи для управління відповідями
    public void addReply(Comment reply) {
        replies.add(reply);
        reply.setParent(this);
    }

    public void removeReply(Comment reply) {
        replies.remove(reply);
        reply.setParent(null);
    }

    // Службові методи
    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", createdAt=" + createdAt +
                ", edited=" + edited +
                ", replies=" + replies.size() +
                '}';
    }*/
}