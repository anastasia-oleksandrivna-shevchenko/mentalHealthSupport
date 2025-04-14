package com.shevchenko.mentalHealthSupport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JoinCommunityController {
    @GetMapping("/register")
    public String showJoinPage() {
        return "join-community";
    }
}
