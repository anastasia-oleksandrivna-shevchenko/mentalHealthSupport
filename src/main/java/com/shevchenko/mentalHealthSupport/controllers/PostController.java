package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {

    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/category/{category}")
    public String postsByCategory(@PathVariable("category") String category, Model model) {
        model.addAttribute("category", category);
        List<Post> posts = postRepository.findByCategoryName(category);
        model.addAttribute("posts", posts);
        return "posts";
    }
    @GetMapping("")
    public String index(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("recentPosts", posts);
        return "index";
    }
}
