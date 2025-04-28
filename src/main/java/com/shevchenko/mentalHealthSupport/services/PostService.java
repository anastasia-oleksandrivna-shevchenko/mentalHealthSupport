package com.shevchenko.mentalHealthSupport.services;

import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByCategory(long id) {
        return postRepository.findByCategoryCategoryid(id);
    }
    public List<Post> getPostsByUser(long id) {
        return postRepository.findByUserId(id);
    }

    public  List<Post> getPostsByCategoryName(String categoryName) {
        return postRepository.findByCategoryName(categoryName);
    }

    public Post createPost(Post post) {
        post.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public int countUniqueUsersByCategory(Long categoryId) {
        return postRepository.countDistinctUsersByCategoryId(categoryId);
    }

}
