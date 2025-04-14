package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Comment;
import com.shevchenko.mentalHealthSupport.models.Category;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CommentsController {
    @GetMapping("/post/{id}")
    public String postsByCategory(@PathVariable("id") Long id, Model model) {
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
        post1.setId(1L);
        post1.setTitle("Причини депресії");
        post1.setContent("Основні причини.");
        post1.setAuthor(user);
        post1.setCategory(category2);
        post1.setCreatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Корисні поради");
        post2.setContent("Як побороти депресію.");
        post2.setAuthor(user);
        post2.setCategory(category2);
        post2.setCreatedAt(LocalDateTime.now());

        Post post3 = new Post();
        post2.setId(3L);
        post3.setTitle("Що робити якщо є здогадка про депресію");
        post3.setContent("Поради щодо здогадки.");
        post3.setAuthor(user);
        post3.setCategory(category2);
        post3.setCreatedAt(LocalDateTime.now());

        Post post4 = new Post();
        post4.setId(4L);
        post4.setTitle("Види депресії");
        post4.setContent("Які є види депресії.");
        post4.setAuthor(user);
        post4.setCategory(category2);
        post4.setCreatedAt(LocalDateTime.now());

        List<Post> posts = List.of(post1, post2, post3, post4);
        List<Comment> comments = List.of();

        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        model.addAttribute("formatter", formatter);

        List<Comment> filteredComments = comments.stream()
                .filter(comment -> comment.getPost().getId().equals(id))
                .toList();
        model.addAttribute("comments", filteredComments);

        return "comments";
    }
}
