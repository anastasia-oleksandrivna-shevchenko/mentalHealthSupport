package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryCategoryid(Long categoryId);
    List<Post> findByUserId(Long userId);
    List<Post> findByCategoryName(String categoryName);
    Optional<Post> findByPostid(Long postid);

    @Query("SELECT COUNT(DISTINCT p.user.id) FROM Post p WHERE p.category.categoryid = :categoryId")
    int countDistinctUsersByCategoryId(@Param("categoryId") Long categoryId);


}
