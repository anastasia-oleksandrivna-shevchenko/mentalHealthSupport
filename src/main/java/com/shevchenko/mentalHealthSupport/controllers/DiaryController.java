package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Diary;
import com.shevchenko.mentalHealthSupport.models.Tag;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.DiaryRepository;
import com.shevchenko.mentalHealthSupport.repositories.TagRepository;
import com.shevchenko.mentalHealthSupport.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class DiaryController {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;


    @GetMapping("/diary")
    public String showDiary(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        String username = userDetails.getUsername();

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        if (username == null) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/diary";
        }

        List<Diary> diaries = diaryRepository.findByUserId(optionalUser.get().getId());
        model.addAttribute("diaryEntries", diaries);
        model.addAttribute("currentUser", optionalUser.get());
        return "diary";
    }

    @PostMapping("/diary")
    public String addDiary(@RequestParam("content") String content,
                           @RequestParam("mood") String mood,
                           @RequestParam("isPrivate") Boolean isPrivate,
                           @RequestParam(required = false) List<String> tags,
                           @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        if (username == null) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/diary";
        }

        User currentUser = optionalUser.get();


        Diary diary = new Diary();
        diary.setUser(currentUser);
        diary.setContent(content);
        diary.setMood(mood);
        diary.setPrivateStatus(isPrivate);
        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());

        Set<Tag> tagSet = new HashSet<>();
        if (tags != null) {
            for (String tagName : tags) {
                Optional<Tag> tagOptional = tagRepository.findByName(tagName);
                Tag tag = tagOptional.orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });
                tagSet.add(tag);
            }
        }
        diary.setTags(tagSet);


        diaryRepository.save(diary);

        return "redirect:/diary";
    }

    @GetMapping("/diary/delete/{id}")
    public String deleteDiaryEntry(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username == null) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<Diary> optionalDiary = diaryRepository.findById(id);
        if (optionalDiary.isPresent()) {
            Diary diary = optionalDiary.get();
            if (diary.getUser().getId().equals(optionalUser.get().getId())) {
                diaryRepository.delete(diary);
            }
        }

        return "redirect:/diary";
    }

    @GetMapping("/diary/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username == null) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/diary";
        }
        model.addAttribute("currentUser", optionalUser.get());

        Optional<Diary> optionalDiary = diaryRepository.findById(id);
        if (optionalDiary.isEmpty()) {
            return "redirect:/diary";
        }

        Diary diary = optionalDiary.get();
        if (!diary.getUser().getId().equals(optionalUser.get().getId())) {
            return "redirect:/diary";
        }

        model.addAttribute("diaryEntry", diary);
        return "edit-diary";
    }

    @PostMapping("/diary/edit/{id}")
    public String updateDiary(@PathVariable("id") Integer id,
                              @RequestParam("content") String content,
                              @RequestParam("mood") String mood,
                              @RequestParam("isPrivate") Boolean isPrivate,
                              @RequestParam(required = false) List<String> tags,
                              @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        if (username == null) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/diary";
        }

        Optional<Diary> optionalDiary = diaryRepository.findById(id);
        if (optionalDiary.isEmpty()) {
            return "redirect:/diary";
        }

        Diary diary = optionalDiary.get();
        if (!diary.getUser().getId().equals(optionalUser.get().getId())) {
            return "redirect:/diary";
        }

        diary.setContent(content);
        diary.setMood(mood);
        diary.setPrivateStatus(isPrivate);
        diary.setUpdatedAt(LocalDateTime.now());
        Set<Tag> tagSet = new HashSet<>();
        if (tags != null) {
            for (String tagName : tags) {
                Optional<Tag> tagOptional = tagRepository.findByName(tagName);
                Tag tag = tagOptional.orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });
                tagSet.add(tag);
            }
        }
        diary.setTags(tagSet);

        diaryRepository.save(diary);

        return "redirect:/diary";
    }
}
