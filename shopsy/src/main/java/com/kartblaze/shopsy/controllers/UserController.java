package com.kartblaze.shopsy.controllers;

import com.kartblaze.shopsy.entities.User;
import com.kartblaze.shopsy.entities.Address;
import com.kartblaze.shopsy.entities.Order;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.services.UserService;
import com.kartblaze.shopsy.services.OrderService;
import com.kartblaze.shopsy.services.EmailService;
import com.kartblaze.shopsy.services.CartService;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserController(UserService userService, OrderService orderService, CartService cartService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model, org.springframework.web.context.request.WebRequest request) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEmailVerified(false);
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userService.save(user);

        // Send verification email
        String appUrl = request.getContextPath();
        String verifyUrl = appUrl + "/verify-email?token=" + token;
        String subject = "Shopsy Email Verification";
        String text = "Please verify your email by clicking the following link: " + verifyUrl;
        emailService.sendEmail(user.getEmail(), subject, text);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);
        List<Order> orders = List.of(); // Placeholder empty list until OrderService method is implemented
        List<Address> addresses = userService.getUserAddresses(user);
        List<Product> wishlist = userService.getUserWishlist(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("addresses", addresses);
        model.addAttribute("wishlist", wishlist);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Model model, Authentication authentication) {
        String username = authentication.getName();
        var userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            userService.save(user);
            model.addAttribute("user", user);
            model.addAttribute("success", "Profile updated successfully");
        }
        return "profile";
    }

    @PostMapping("/profile/address/add")
    public String addAddress(@ModelAttribute("address") Address address, Authentication authentication) {
        String username = authentication.getName();
        var userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            userService.addAddress(userOpt.get(), address);
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/address/update")
    public String updateAddress(@ModelAttribute("address") Address address) {
        userService.updateAddress(address);
        return "redirect:/profile";
    }

    @PostMapping("/profile/address/delete")
    public String deleteAddress(@org.springframework.web.bind.annotation.RequestParam("id") Long addressId) {
        userService.deleteAddress(addressId);
        return "redirect:/profile";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@org.springframework.web.bind.annotation.RequestParam("token") String token, Model model) {
        var userOpt = userService.findByVerificationToken(token);
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            user.setEmailVerified(true);
            user.setVerificationToken(null);
            userService.save(user);
            model.addAttribute("message", "Email verified successfully. You can now log in.");
        } else {
            model.addAttribute("error", "Invalid or expired verification token.");
        }
        return "login";
    }
}
