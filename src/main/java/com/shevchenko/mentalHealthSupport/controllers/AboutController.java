package com.shevchenko.mentalHealthSupport.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String showAboutPage(Model model,
                                @AuthenticationPrincipal UserDetails userDetails,
                                HttpServletRequest request) {
        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("_csrf", csrfToken);

        return "about";
    }
}
