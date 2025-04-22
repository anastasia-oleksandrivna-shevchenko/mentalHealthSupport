package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByUserId(Long userId);

}
