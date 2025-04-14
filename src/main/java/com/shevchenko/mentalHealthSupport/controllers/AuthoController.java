package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class AuthoController {

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "redirect", required = false) String redirectUrl,
            Model model) {

        if (redirectUrl != null) {
            model.addAttribute("redirectUrl", redirectUrl);
        }

        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPage(
            @RequestParam(value = "redirect", required = false) String redirectUrl,
            Model model) {

        return "forgot-password";
    }


    @PostMapping("/login")
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "redirect", required = false) String redirectUrl,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Введіть ім'я користувача та пароль");
                return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
            }

            // Пошук користувача за ім'ям
            // User user = userRepository.findByUsername(username);
            // if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            //     redirectAttributes.addFlashAttribute("errorMessage", "Неправильне ім'я користувача або пароль");
            //     return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
            // }

            if ("anastasia".equals(username) && "password123".equals(password)) {
                User user = new User();
                user.setUsername("anastasia");
                user.setEmail("anastasia@example.com");
                user.setRegisteredAt(LocalDateTime.now().minusDays(30));
                user.setAvatar("/images/avatars/default.png");
                user.setBio("Привіт! Я учасник спільноти взаємодопомоги.");


                return redirectUrl != null ? "redirect:" + redirectUrl : "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Неправильне ім'я користувача або пароль");
                return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Виникла помилка під час входу");
            return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
        }
    }
}
