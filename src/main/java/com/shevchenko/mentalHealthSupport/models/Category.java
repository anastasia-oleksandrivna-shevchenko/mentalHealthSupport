package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryid;

    private String name;
    private String description;
    private String image_url;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts;

}