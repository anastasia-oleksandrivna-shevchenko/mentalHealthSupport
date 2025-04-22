package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Diary> diaries;

    @ManyToMany(mappedBy = "posts")
    private Set<Post> posts;

}
