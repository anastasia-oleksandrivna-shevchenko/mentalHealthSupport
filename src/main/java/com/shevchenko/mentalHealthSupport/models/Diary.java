package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "diary_entries")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String mood;

    private boolean isPrivate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToMany
    @JoinTable(
            name = "diary_tags",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;



    // Конструктори
    /*public Diary() {
        this.tags = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isPrivate = true;
    }

    public Diary(User user, String content, String mood) {
        this();
        this.user = user;
        this.content = content;
        this.mood = mood;
    }

    public Diary(User user, String content, String mood, List<String> tags, boolean isPrivate) {
        this(user, content, mood);
        if (tags != null) {
            this.tags = tags;
        }
        this.isPrivate = isPrivate;
    }

    // Getters і Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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

    // Методи для агрегації даних і статистики
    public String getMoodEmoji() {
        return switch (mood) {
            case "great" -> "😄";
            case "good" -> "🙂";
            case "neutral" -> "😐";
            case "bad" -> "😔";
            case "awful" -> "😞";
            default -> "😐";
        };
    }

    public int getMoodScore() {
        return switch (mood) {
            case "great" -> 5;
            case "good" -> 4;
            case "neutral" -> 3;
            case "bad" -> 2;
            case "awful" -> 1;
            default -> 3;
        };
    }

    // Перевизначені методи Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diary that = (Diary) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "id=" + id +
                ", mood='" + mood + '\'' +
                ", createdAt=" + createdAt +
                ", isPrivate=" + isPrivate +
                '}';
    }*/
}
