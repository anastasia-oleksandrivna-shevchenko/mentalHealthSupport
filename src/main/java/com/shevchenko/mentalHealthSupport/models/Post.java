package com.shevchenko.mentalHealthSupport.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postid;

    private String title;
    private String content;
    private boolean is_anonymous;
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    /*private String title;
    private String content;
    private String excerpt;
    private User author;
    private Category category;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;
    private int replyCount;
    private int likeCount;
    private int helpfulCount;
    private int bookmarkCount;
    private List<Comment> comments;
    private List<Post> relatedPosts;*/

    // Конструктори
    /*public Post() {
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.relatedPosts = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Post(Long id, String title, String content, User author, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.excerpt = generateExcerpt(content);
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.relatedPosts = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.viewCount = 0;
        this.replyCount = 0;
        this.likeCount = 0;
        this.helpfulCount = 0;
        this.bookmarkCount = 0;
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.excerpt = generateExcerpt(content);
        this.updatedAt = LocalDateTime.now();
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
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

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.replyCount = comments.size();
    }

    public List<Post> getRelatedPosts() {
        return relatedPosts;
    }

    public void setRelatedPosts(List<Post> relatedPosts) {
        this.relatedPosts = relatedPosts;
    }

    // Методи для управління тегами
    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // Методи для управління комментарями
    public void addComment(Comment comment) {
        comments.add(comment);
        replyCount = comments.size();
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        replyCount = comments.size();
    }

    // Службові методи
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void incrementBookmarkCount() {
        this.bookmarkCount++;
    }

    private String generateExcerpt(String content) {
        if (content == null) {
            return "";
        }

        // Видаляємо HTML-теги, якщо вони є
        String plainText = content.replaceAll("<[^>]*>", "");

        // Обмежуємо довжину
        int maxLength = 200;
        if (plainText.length() <= maxLength) {
            return plainText;
        }

        // Шукаємо останній пробіл перед обмеженням
        int lastSpace = plainText.lastIndexOf(' ', maxLength);
        if (lastSpace > 0) {
            return plainText.substring(0, lastSpace) + "...";
        } else {
            return plainText.substring(0, maxLength) + "...";
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", category=" + (category != null ? category.getName() : "null") +
                ", createdAt=" + createdAt +
                ", replyCount=" + replyCount +
                '}';
    }*/
}