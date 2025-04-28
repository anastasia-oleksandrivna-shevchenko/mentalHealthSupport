package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Diary;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.DiaryRepository;
import com.shevchenko.mentalHealthSupport.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    HttpSession session;

    @GetMapping("/register")
    public String register() {
        return "join-community";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String changePassword(@RequestParam String password1,
                                 @RequestParam String password2,
                                 @RequestParam String username1,
                                 Model model) {


        /*if (username == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Будь ласка, увійдіть в систему для зміни пароля");
            return "redirect:/login";  // Якщо користувач не увійшов в систему
        }*/

        if (username1 == null || username1.isEmpty()) {
            model.addAttribute("errorMessage", "Логін не може бути порожнім");
            return "redirect:/forgot-password";
        }

        if (password1 == null || password1.isEmpty() || password2 == null || password2.isEmpty()) {
            model.addAttribute("errorMessage", "Пароль не може бути порожнім");
            return "redirect:/forgot-password";
        }

        if (!password1.equals(password2)) {
            model.addAttribute("errorMessage", "Паролі не співпадають");
            return "redirect:/forgot-password";
        } else {


            // Шукаємо користувача за логіном (username)
            Optional<User> optionalUser = userRepository.findByUsername(username1);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Оновлюємо пароль користувача
                user.setPassword(password1);
                userRepository.save(user);  // Зберігаємо зміни в базі даних

                model.addAttribute("successMessage", "Пароль успішно змінено");
                return "redirect:/login";  // Перенаправлення на сторінку логіну після зміни пароля
            } else {
                model.addAttribute("errorMessage", "Користувача з таким логіном не знайдено");
                return "redirect:/forgot-password";  // Якщо користувач не знайдений
            }
        }
    }


    // Сторінка логіну
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        String errorMessage = (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage"); // Видаляємо помилку з сесії після її використання
        }
        return "login";
    }

    // Обробка логіну
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isEmpty()) {
            session.setAttribute("errorMessage", "Неправильне ім'я користувача або пароль");
            return "redirect:/login";
        }
        session.setAttribute("username", username);
        // Тут можна ще зберігати ім’я користувача в моделі або десь ще — якщо потрібно
        return "redirect:/"; // Просто редірект на головну
    }

    // Сторінка профілю
    @GetMapping("/profile")
    public String showMyProfile(Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/profile";
        }

        // Якщо є повідомлення про помилку, передаємо його в модель
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage"); // Видаляємо після використання
        }

        model.addAttribute("user", optionalUser.get());
        return "my-profile";
    }

    // Сторінка журналу
    @GetMapping("/diary")
    public String showDiary(Model model) {
        String username = (String) session.getAttribute("username");
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

    // Додавання запису в журнал
    @PostMapping("/diary")
    public String addDiary(@RequestParam("content") String content,
                           @RequestParam("mood") String mood,
                           @RequestParam("isPrivate") Boolean isPrivate) {

        String username = (String) session.getAttribute("username");

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
        diaryRepository.save(diary);

        return "redirect:/diary";
    }

    @GetMapping("/diary/delete/{id}")
    public String deleteDiaryEntry(@PathVariable("id") Integer id) {
        String username = (String) session.getAttribute("username");
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
            // Перевірка: чи запис належить поточному користувачу
            if (diary.getUser().getId().equals(optionalUser.get().getId())) {
                diaryRepository.delete(diary);
            }
        }

        return "redirect:/diary";
    }

    @GetMapping("/diary/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        String username = (String) session.getAttribute("username");
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
            return "redirect:/diary"; // Якщо немає запису
        }

        Diary diary = optionalDiary.get();
        // Перевірка: чи цей запис належить користувачу
        if (!diary.getUser().getId().equals(optionalUser.get().getId())) {
            return "redirect:/diary"; // Немає прав
        }

        model.addAttribute("diaryEntry", diary);
        return "edit-diary"; // Переходимо на сторінку редагування
    }

    @PostMapping("/diary/edit/{id}")
    public String updateDiary(@PathVariable("id") Integer id,
                              @RequestParam("content") String content,
                              @RequestParam("mood") String mood,
                              @RequestParam("isPrivate") Boolean isPrivate) {

        String username = (String) session.getAttribute("username");
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
        // Перевірка: чи запис належить користувачу
        if (!diary.getUser().getId().equals(optionalUser.get().getId())) {
            return "redirect:/diary";
        }

        // Оновлюємо поля
        diary.setContent(content);
        diary.setMood(mood);
        diary.setPrivateStatus(isPrivate);
        diary.setUpdatedAt(LocalDateTime.now());

        diaryRepository.save(diary);

        return "redirect:/diary";
    }



}
