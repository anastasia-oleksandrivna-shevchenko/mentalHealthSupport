package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.*;
import com.shevchenko.mentalHealthSupport.repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    HttpSession session;

    @GetMapping("/register")
    public String register() {
        return "join-community";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String avatar,
                               @RequestParam String bio,
                               @RequestParam Boolean termsAgree,
                               @RequestParam String lastName,
                               @RequestParam String firstName,
                               @RequestParam Integer age,
                               @RequestParam String gender,
                               Model model) {

        if (termsAgree == null || !termsAgree) {
            model.addAttribute("error", "Ви повинні погодитись з умовами користування.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Паролі не співпадають.");
            return "register";
        }

        Optional<User> optionalUserLogin = userRepository.findByUsername(username);
        if (!optionalUserLogin.isEmpty()) {
            model.addAttribute("error", "Ім'я користувача вже зайняте.");
            return "register";
        }
        Optional<User> optionalUserEmail = userRepository.findByEmail(email);

        if (!optionalUserEmail.isEmpty()) {
            model.addAttribute("error", "Електронна пошта вже використовується.");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAvatar_url(avatar);
        user.setBio(bio);
        user.setPassword(password);
        user.setLast_name(lastName);
        user.setFirst_name(firstName);
        user.setAge(age);
        user.setGender(gender);
        user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        userRepository.save(user);

        return "redirect:/login";
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

            Optional<User> optionalUser = userRepository.findByUsername(username1);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setPassword(password1);
                userRepository.save(user);

                model.addAttribute("successMessage", "Пароль успішно змінено");
                return "redirect:/login";
            } else {
                model.addAttribute("errorMessage", "Користувача з таким логіном не знайдено");
                return "redirect:/forgot-password";
            }
        }
    }


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        String errorMessage = (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isEmpty()) {
            session.setAttribute("errorMessage", "Неправильне ім'я користувача або пароль");
            return "redirect:/login";
        }
        session.setAttribute("username", username);
        return "redirect:/";
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

        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        model.addAttribute("user", optionalUser.get());
        return "my-profile";
    }

    @PostMapping("/profile/change-password")
    public String changePasswordProfile(@RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword,
                                 @RequestParam String currentPassword,
                                 Model model) {


        String username = (String) session.getAttribute("username");

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, currentPassword);
        if (optionalUser.isEmpty()) {
            session.setAttribute("errorMessage", "Неправильний пароль");
            return "redirect:/profile";
        }


        if (newPassword == null || newPassword.isEmpty() || confirmNewPassword == null || confirmNewPassword.isEmpty()) {
            model.addAttribute("errorMessage", "Пароль не може бути порожнім");
            return "redirect:/forgot-password";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("errorMessage", "Паролі не співпадають");
            return "redirect:/forgot-password";
        } else {
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setPassword(newPassword);
                userRepository.save(user);

                return "redirect:/profile";
            } else {

                return "redirect:/profile";
            }
        }
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String email,
                                @RequestParam(required = false) String avatar,
                                @RequestParam(required = false) String bio) {

        String username = (String) session.getAttribute("username");

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setUsername(username);
            user.setEmail(email);
            user.setAvatar_url(avatar);
            user.setBio(bio);
            userRepository.save(user);

            return "redirect:/profile";
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?redirect=/profile";
        }

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return "redirect:/login";
        }

        return "redirect:/profile";
    }












}
