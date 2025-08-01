package com.kartblaze.shopsy.controllers;

// ...existing code...
// ...existing code...
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class OrderController {

    @GetMapping("/orders")
    public String orders(Model model, Authentication authentication) {
        if (authentication == null) return "redirect:/login";
        // Dummy data for template demo:
        model.addAttribute("orders", List.of());
        return "orders";
    }

    @GetMapping("/place-order")
    public String placeOrder() {
        return "place-order";
    }
}
