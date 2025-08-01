package com.kartblaze.shopsy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // This single endpoint serves our entire SPA
        return "index";
    }
}