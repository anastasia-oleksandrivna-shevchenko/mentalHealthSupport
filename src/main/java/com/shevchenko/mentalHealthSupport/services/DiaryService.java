package com.shevchenko.mentalHealthSupport.services;

import com.shevchenko.mentalHealthSupport.models.Diary;
import com.shevchenko.mentalHealthSupport.repositories.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    @Autowired
    DiaryRepository diaryRepository;

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }
    public List<Diary> findByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }
    public List<Diary> findPublicEntries() {
        return diaryRepository.findByIsPrivateStatusFalse();
    }
    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }

    public void delete(Integer id) {
        diaryRepository.deleteById(id);
    }
}
