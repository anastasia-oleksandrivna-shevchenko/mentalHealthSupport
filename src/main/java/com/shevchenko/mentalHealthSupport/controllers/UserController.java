package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Diary;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.DiaryRepository;
import com.shevchenko.mentalHealthSupport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "redirect", required = false) String redirectUrl, Model model) {
        if (redirectUrl != null) {
            model.addAttribute("redirectUrl", redirectUrl);
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "redirect", required = false) String redirectUrl, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User currentUser) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неправильне ім'я користувача або пароль");
            return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
        }

        currentUser = optionalUser.get();
        return redirectUrl != null ? "redirect:" + redirectUrl : "redirect:/";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Інструкції надіслано на email.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Користувача не знайдено.");
        }
        return "redirect:/forgot-password";
    }

    @GetMapping("/my-profile")
    public String showMyProfile(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login?redirect=/my-profile";
        }
        model.addAttribute("user", currentUser);
        return "my-profile";
    }

    @GetMapping("/register")
    public String showJoinCommunity() {
        return "join-community";
    }

    @GetMapping("/diary")
    public String showDiary(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login?redirect=/diary";
        }
        List<Diary> diaries = diaryRepository.findByUserId(currentUser.getId());
        model.addAttribute("diaryEntries", diaries);
        model.addAttribute("currentUser", currentUser);
        return "diary";
    }

    @PostMapping("/diary")
    public String addDiary(@RequestParam("content") String content,
                           @RequestParam("mood") String mood,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            return "redirect:/login?redirect=/diary";
        }

        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Вміст запису не може бути порожнім.");
            return "redirect:/diary";
        }

        Diary diary = new Diary();
        diary.setUser(currentUser);
        diary.setContent(content);
        diary.setMood(mood);
        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());
        diaryRepository.save(diary);

        redirectAttributes.addFlashAttribute("message", "Запис додано.");
        return "redirect:/diary";
    }
}
