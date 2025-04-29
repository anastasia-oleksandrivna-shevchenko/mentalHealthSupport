package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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
