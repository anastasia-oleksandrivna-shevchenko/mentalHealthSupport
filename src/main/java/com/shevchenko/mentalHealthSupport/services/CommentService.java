package com.shevchenko.mentalHealthSupport.services;

import com.shevchenko.mentalHealthSupport.models.Comment;
import com.shevchenko.mentalHealthSupport.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostPostid(postId);
    }
    public Comment save(Comment comment) {
        comment.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return commentRepository.save(comment);
    }
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
