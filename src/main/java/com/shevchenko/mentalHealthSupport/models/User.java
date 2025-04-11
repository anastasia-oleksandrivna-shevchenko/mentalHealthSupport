package com.shevchenko.mentalHealthSupport.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private String bio;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLogin;
    private List<Post> posts;
    private List<Comment> comments;
    private Set<String> roles;

    // Конструктори
    public User() {
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.roles = new HashSet<>();
        this.registeredAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = "default.png";
        this.bio = "";
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.roles = new HashSet<>();
        this.registeredAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // Методи для управління ролями
    public void addRole(String role) {
        roles.add(role);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    // Методи для управління постами користувача
    public void addPost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }

    // Методи для управління коментарями користувача
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    // Службові методи
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
