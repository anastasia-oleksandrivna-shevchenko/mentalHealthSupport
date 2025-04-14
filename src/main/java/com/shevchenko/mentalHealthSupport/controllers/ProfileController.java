package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("")
    public String showProfile(Model model) {

        User currentUser = getUserFromSession();

        if (currentUser == null) {
            return "redirect:/login?redirect=/profile";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        model.addAttribute("user", currentUser);
        model.addAttribute("formatter", formatter);

        return "my-profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "bio", required = false) String bio,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            User currentUser = getUserFromSession();

            if (currentUser == null) {
                return "redirect:/login?redirect=/profile";
            }

            if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ім'я користувача та електронна пошта обов'язкові для заповнення");
                return "redirect:/profile";
            }


            if (!username.equals(currentUser.getUsername())) {
                // Перевірка в базі даних, що ім'я користувача не зайняте
                // if (userRepository.existsByUsername(username)) {
                //    redirectAttributes.addFlashAttribute("errorMessage", "Це ім'я користувача вже використовується");
                //    return "redirect:/profile";
                // }
            }

            // Перевірка унікальності електронної пошти, якщо вона змінилася
            if (!email.equals(currentUser.getEmail())) {
                // Перевірка в базі даних, що електронна пошта не зайнята
                // if (userRepository.existsByEmail(email)) {
                //    redirectAttributes.addFlashAttribute("errorMessage", "Ця електронна пошта вже використовується");
                //    return "redirect:/profile";
                // }
            }

            currentUser.setUsername(username);
            currentUser.setEmail(email);

            if (avatar != null) {
                currentUser.setAvatar(avatar.trim().isEmpty() ? null : avatar);
            }

            if (bio != null) {
                currentUser.setBio(bio.trim().isEmpty() ? null : bio);
            }

            // Збереження оновленого користувача в базу даних
            // userRepository.save(currentUser);

            redirectAttributes.addFlashAttribute("successMessage", "Профіль успішно оновлено");

        } catch (Exception e) {
            // Log error
            // logger.error("Помилка оновлення профілю", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Виникла помилка при оновленні профілю");
        }

        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            RedirectAttributes redirectAttributes) {

        try {
            User currentUser = getUserFromSession();

            // Перевірка, що поточний пароль правильний
            // if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            //    redirectAttributes.addFlashAttribute("errorMessage", "Поточний пароль неправильний");
            //    return "redirect:/profile";
            // }

            // Перевірка, що новий пароль відповідає вимогам (мінімум 8 символів, літери та цифри)
            if (newPassword.length() < 8 || !newPassword.matches(".*[a-zA-Z].*") || !newPassword.matches(".*\\d.*")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Новий пароль повинен містити мінімум 8 символів, включаючи літери та цифри");
                return "redirect:/profile";
            }

            // Перевірка, що новий пароль та підтвердження сходяться
            if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Новий пароль та підтвердження не співпадають");
                return "redirect:/profile";
            }

            // Хешування нового пароля та оновлення
            // currentUser.setPassword(passwordEncoder.encode(newPassword));

            // Збереження оновленого користувача
            // userRepository.save(currentUser);

            redirectAttributes.addFlashAttribute("successMessage", "Пароль успішно змінено");

        } catch (Exception e) {
            // Log error
            // logger.error("Помилка зміни пароля", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Виникла помилка при зміні пароля");
        }

        return "redirect:/profile";
    }



    @PostMapping("/delete")
    public String deleteProfile(RedirectAttributes redirectAttributes) {
        try {
            // Отримати поточного користувача
            User currentUser = getUserFromSession();

            // Видалення користувача
            // userRepository.delete(currentUser);

            // Завершення сесії
            // httpSession.invalidate();

            // Перенаправлення на головну сторінку
            return "redirect:/";

        } catch (Exception e) {
            // Log error
            // logger.error("Помилка видалення профілю", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Виникла помилка при видаленні профілю");
            return "redirect:/profile";
        }
    }

    private User getUserFromSession() {
        // Створюємо тестового користувача для прикладу
        User user = new User();
        user.setUsername("anastasia");
        user.setEmail("anastasia@example.com");
        user.setRegisteredAt(java.time.LocalDateTime.now().minusDays(30)); // Зареєстрований 30 днів тому
        user.setAvatar("/images/avatars/default.png");
        user.setBio("Привіт! Я учасник спільноти взаємодопомоги.");

        return user;
    }
}