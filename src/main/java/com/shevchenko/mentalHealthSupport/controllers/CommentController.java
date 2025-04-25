package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Comment;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.repositories.CommentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CommentController {

    private CommentRepository commentRepository;

    @GetMapping("/category/{post}")
    public String postsByCategory(@PathVariable("post") Long post, Model model) {
        model.addAttribute("post", post);
        List<Comment> comments = commentRepository.findByPostId(post);
        model.addAttribute("comments", comments);
        return "comments";
    }
}
