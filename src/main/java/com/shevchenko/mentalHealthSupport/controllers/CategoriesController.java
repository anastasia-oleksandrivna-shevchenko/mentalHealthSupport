package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CategoriesController {

    @GetMapping("/categories")
    public String index(Model model) {

        // Створюємо категорію
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Психологічна підтримка при особистих проблемах");
        category1.setDescription("Обговорення проблем і рішень");
        category1.setLastUpdated(LocalDateTime.now());
        category1.setImageUrl("/images/kolorova.png");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Депресія");
        category2.setDescription("Обговорення причин, наслідків та лікування депресії.");
        category2.setLastUpdated(LocalDateTime.now());
        category2.setImageUrl("/images/depresion.jpg");

        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("Апатія");
        category3.setDescription("Причини апатії. Сезонне явище апатії. Поради щодо боротьби");
        category3.setLastUpdated(LocalDateTime.now());
        category3.setImageUrl("/images/apatia.png");

        Category category4 = new Category();
        category4.setId(4L);
        category4.setName("Вигорання");
        category4.setDescription("Вигорання: основні причини, рішення, поради");
        category4.setLastUpdated(LocalDateTime.now());
        category4.setImageUrl("/images/vugoranie.jpg");



        List<Category> categories = List.of(category1, category2 , category3,  category4);

        model.addAttribute("categories", categories);


        return "categories";
    }
}
