package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
                ", isPrivate=" + isPrivate +
                '}';
    }
}
