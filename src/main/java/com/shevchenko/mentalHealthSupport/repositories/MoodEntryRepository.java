package com.shevchenko.mentalHealthSupport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry,Long> {
    List<MoodEntry> findByUserId(Long categoryId);
}
