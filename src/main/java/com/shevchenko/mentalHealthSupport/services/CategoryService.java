package com.shevchenko.mentalHealthSupport.services;

import com.shevchenko.mentalHealthSupport.models.Category;
import com.shevchenko.mentalHealthSupport.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }


}
