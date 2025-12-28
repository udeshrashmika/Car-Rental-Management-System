package com.example.car_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


    @Controller
    public class LoginController {

        @GetMapping("/login")
        public String showLoginPage() {
            return "login"; // මෙයින් templates/login.html පිටුව පෙන්වයි
        }
    }


