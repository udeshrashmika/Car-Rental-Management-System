package com.example.car_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String showLandingPage() {
        return "landing";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This looks for login.html
    }
}