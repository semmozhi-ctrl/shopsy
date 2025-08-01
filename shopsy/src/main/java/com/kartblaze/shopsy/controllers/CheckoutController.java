package com.kartblaze.shopsy.controllers;

import com.kartblaze.shopsy.entities.Cart;
import com.kartblaze.shopsy.entities.CartItem;
import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.repositories.CartRepository;
import com.kartblaze.shopsy.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CheckoutController(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String checkout(Model model, java.security.Principal principal) {
        if (principal == null) {
            model.addAttribute("cartItems", new java.util.ArrayList<>());
            model.addAttribute("totalPrice", 0);
            return "checkout";
        }
        AppUser user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            model.addAttribute("cartItems", new java.util.ArrayList<>());
            model.addAttribute("totalPrice", 0);
            return "checkout";
        }
        Cart cart = cartRepository.findByUser(user).orElse(null);
        java.util.List<CartItem> cartItems = (cart != null) ? cart.getItems() : new java.util.ArrayList<>();
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/confirm")
    public String confirmCheckout(Model model) {
        // Here you would typically process the order, handle payment, etc.
        // For this example, we'll just clear the cart.
        cartRepository.deleteAll();
        model.addAttribute("message", "Checkout confirmed! Thank you for your order.");
        return "checkout-confirmation";
    }
}
