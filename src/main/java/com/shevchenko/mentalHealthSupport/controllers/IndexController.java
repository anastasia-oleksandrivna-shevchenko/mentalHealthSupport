package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Category;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("")
    public String index(Model model) {
        // Створюємо користувача
        User user = new User();
        user.setUsername("anastasia");
        user.setEmail("anastasia@example.com");
        user.setRegisteredAt(LocalDateTime.now());

        // Створюємо категорії
        Category category1 = new Category();
        category1.setName("Психологічна підтримка при особистих проблемах");
        category1.setDescription("Обговорення проблем і рішень");
        category1.setLastUpdated(LocalDateTime.now());
        category1.setImageUrl("/images/kolorova.png");

        Category category2 = new Category();
        category2.setName("Депресія");
        category2.setDescription("Обговорення причин, наслідків та лікування депресії.");
        category2.setLastUpdated(LocalDateTime.now());
        category2.setImageUrl("/images/depresion.jpg");

        Category category3 = new Category();
        category3.setName("Апатія");
        category3.setDescription("Причини апатії. Сезонне явище апатії. Поради щодо боротьби");
        category3.setLastUpdated(LocalDateTime.now());
        category3.setImageUrl("/images/apatia.png");

        Category category4 = new Category();
        category4.setName("Вигорання");
        category4.setDescription("Вигорання: основні причини, рішення, поради");
        category4.setLastUpdated(LocalDateTime.now());
        category4.setImageUrl("/images/vugoranie.jpg");





        // Створюємо пости
        Post post1 = new Post();
        post1.setTitle("Ласкаво просимо!");
        post1.setContent("Це перше повідомлення на форумі.");
        post1.setAuthor(user);
        post1.setCategory(category1);
        post1.setCreatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("Корисні поради");
        post2.setContent("Як зберігати душевний спокій.");
        post2.setAuthor(user);
        post2.setCategory(category1);
        post2.setCreatedAt(LocalDateTime.now());

        List<Post> recentPosts = List.of(post1, post2);
        List<Category> categories = List.of(category1, category2 , category3,  category4);
        int totalPosts = recentPosts.size();

        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        model.addAttribute("recentPosts", recentPosts);
        model.addAttribute("categories", categories);
        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("formatter", formatter);

        return "index";
    }
}


