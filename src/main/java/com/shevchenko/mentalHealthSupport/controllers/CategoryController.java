package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Category;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.Tag;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String showAllCategories(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        return "categories";
    }

    @GetMapping("/category/new")
    public String showAddForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        return "newCategory";
    }

    @PostMapping("/category/new")
    public String AddNewPost(Model model,
                             @RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String image){



        Category category = new Category();
        category.setName(title);
        category.setDescription(content);
        category.setImageUrl(image);

        categoryRepository.save(category);

        return "redirect:/categories";
    }

    @PostMapping("/category/{id}/delete")
    public String deletePost(@PathVariable("id") Long categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findByCategoryid(categoryId);

        Category category = optionalCategory.get();

        categoryRepository.delete(category);


        return "redirect:/";
    }

}
