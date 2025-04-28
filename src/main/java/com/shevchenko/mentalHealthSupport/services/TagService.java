package com.shevchenko.mentalHealthSupport.services;

import com.shevchenko.mentalHealthSupport.models.Tag;
import com.shevchenko.mentalHealthSupport.repositories.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}
