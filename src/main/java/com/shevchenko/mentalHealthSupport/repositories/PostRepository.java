package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByUserId(Long userId);
    List<Post> findByCategoryName(String categoryName);

    @Query("SELECT COUNT(DISTINCT p.user.id) FROM Post p WHERE p.category.categoryid = :categoryId")
    int countDistinctUsersByCategoryId(@Param("categoryId") Long categoryId);


}
