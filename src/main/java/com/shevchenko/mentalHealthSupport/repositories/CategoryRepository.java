package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
    Optional<Category> findByCategoryid(Long id);
}
