package com.shevchenko.mentalHealthSupport.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private int discussionCount;
    private int userCount;
    private int viewCount;
    private LocalDateTime lastUpdated;
    private List<Post> posts;

    // Конструктори
    public Category() {
        posts = new ArrayList<>();
    }

    public Category(Long id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.discussionCount = 0;
        this.userCount = 0;
        this.viewCount = 0;
        this.lastUpdated = LocalDateTime.now();
        this.posts = new ArrayList<>();
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDiscussionCount() {
        return discussionCount;
    }

    public void setDiscussionCount(int discussionCount) {
        this.discussionCount = discussionCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Методи для управління постами у категорії
    public void addPost(Post post) {
        posts.add(post);
        discussionCount = posts.size();
        lastUpdated = LocalDateTime.now();
    }

    public void removePost(Post post) {
        posts.remove(post);
        discussionCount = posts.size();
        lastUpdated = LocalDateTime.now();
    }

    // Службові методи
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updateUserCount(int count) {
        this.userCount = count;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discussionCount=" + discussionCount +
                ", userCount=" + userCount +
                '}';
    }
}