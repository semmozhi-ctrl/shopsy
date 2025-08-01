package com.kartblaze.shopsy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        // You can add data to the model here if needed
        model.addAttribute("title", "About Us");
        model.addAttribute("content", "This is the about page content.");
        return "about"; // Return the name of the about page template
    }
}