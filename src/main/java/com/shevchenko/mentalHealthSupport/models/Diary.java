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

    @Column(name = "is_private_status")
    public boolean isPrivateStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
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

    // Конструктор без параметрів (необхідно для JPA)
    public Diary() {
    }

    // Конструктор з параметрами (якщо необхідно)
    public Diary(User user, String content, String mood, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.content = content;
        this.mood = mood;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Сетер для поля id
    public void setId(Long id) {
        this.id = id;
    }

    // Сетер для поля content
    public void setContent(String content) {
        this.content = content;
    }

    // Сетер для поля mood
    public void setMood(String mood) {
        this.mood = mood;
    }

    // Сетер для поля isPrivate
    public void setPrivate(boolean isPrivate) {
        this.isPrivateStatus = isPrivate;
    }

    // Сетер для поля createdAt
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Сетер для поля updatedAt
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Сетер для поля user
    public void setUser(User user) {
        this.user = user;
    }

    // Геттер та сетер для id
    public Long getId() {
        return id;
    }



    // Геттер та сетер для content
    public String getContent() {
        return content;
    }



    // Геттер та сетер для mood
    public String getMood() {
        return mood;
    }



    // Геттер та сетер для isPrivateStatus
    public boolean isPrivateStatus() {
        return isPrivateStatus;
    }

    public void setPrivateStatus(boolean isPrivateStatus) {
        this.isPrivateStatus = isPrivateStatus;
    }

    // Геттер та сетер для createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    // Геттер та сетер для updatedAt
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }



    // Геттер та сетер для user
    public User getUser() {
        return user;
    }


    // Геттер та сетер для tags
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
                ", isPrivate=" + isPrivateStatus +
                '}';
    }
}
