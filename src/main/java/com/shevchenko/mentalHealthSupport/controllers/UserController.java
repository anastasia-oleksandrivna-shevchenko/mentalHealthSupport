package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.*;
import com.shevchenko.mentalHealthSupport.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request) {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        //CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        return "join-community";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam(required = false) String avatar,
                               @RequestParam(required = false) String bio,
                               @RequestParam(required = false) Boolean termsAgree,
                               @RequestParam String lastName,
                               @RequestParam String firstName,
                               @RequestParam Integer age,
                               @RequestParam String gender,
                               Model model,
                               HttpServletRequest request) {

        if (termsAgree == null || !termsAgree) {
            model.addAttribute("error", "Ви повинні погодитись з умовами користування.");
            return "join-community";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Паролі не співпадають.");
            return "join-community";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Ім'я користувача вже зайняте.");
            return "join-community";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Електронна пошта вже використовується.");
            return "join-community";
        }

        // Створення користувача
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAvatar_url(avatar);
        user.setBio(bio);
        user.setLast_name(lastName);
        user.setFirst_name(firstName);
        user.setAge(age);
        user.setGender(gender);
        user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext());

        return "redirect:/";

    }


    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String changePassword(@RequestParam String password1,
                                 @RequestParam String password2,
                                 @RequestParam String username1,
                                 RedirectAttributes redirectAttributes) {

        if (username1 == null || username1.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Логін не може бути порожнім");
            return "redirect:/forgot-password";
        }

        if (password1 == null || password1.isEmpty() || password2 == null || password2.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пароль не може бути порожнім");
            return "redirect:/forgot-password";
        }

        if (!password1.equals(password2)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Паролі не співпадають");
            return "redirect:/forgot-password";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username1);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(password1));
            userRepository.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "Пароль успішно змінено");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Користувача з таким логіном не знайдено");
            return "redirect:/forgot-password";
        }
    }


    @GetMapping("/login")
    public String showLoginPage(Model model, HttpServletRequest request) {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        //CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request,
                        Model model) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                System.out.println("Хеш паролю в базі: " + user.getPassword());
                System.out.println("matches: " + passwordEncoder.matches(password, user.getPassword()));
            } else {
                System.out.println("Користувача не знайдено");
            }


            return "redirect:/";

        } catch (AuthenticationException ex) {
            model.addAttribute("errorMessage", "Невірне ім'я користувача або пароль");
            return "login";
        }
    }


    // Сторінка профілю
    @GetMapping("/profile")
    public String showMyProfile(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        String username = userDetails.getUsername();

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        //CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

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
    public String changePasswordProfile(@RequestParam String currentPassword,
                                        @RequestParam String newPassword,
                                        @RequestParam String confirmNewPassword,
                                        RedirectAttributes redirectAttributes,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Користувача не знайдено");
            return "redirect:/profile";
        }

        User user = optionalUser.get();

        // Перевірка поточного пароля
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неправильний поточний пароль");
            return "redirect:/profile";
        }

        if (newPassword == null || newPassword.isEmpty() || confirmNewPassword == null || confirmNewPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Новий пароль не може бути порожнім");
            return "redirect:/profile";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Нові паролі не співпадають");
            return "redirect:/profile";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Пароль змінено");
        return "redirect:/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String email,
                                @RequestParam(required = false) String avatar,
                                @RequestParam(required = false) String bio,
                                @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            user.setAvatar_url(avatar);
            user.setBio(bio);
            userRepository.save(user);
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Вийти вручну
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }


}
