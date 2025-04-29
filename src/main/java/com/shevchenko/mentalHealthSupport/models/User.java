package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar_url;
    private Integer age;
    private String gender;
    private String bio;
    private Timestamp created_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diary> diaryEntries;

    @Transient
    public String getFormattedCreatedAt() {
        return created_at.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
