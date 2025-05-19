package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.repositories.CategoryRepository;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("recentPosts", postRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }
}
