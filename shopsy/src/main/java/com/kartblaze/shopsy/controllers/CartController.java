package com.kartblaze.shopsy.controllers;

import com.kartblaze.shopsy.entities.Cart;
import com.kartblaze.shopsy.entities.CartItem;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.repositories.CartRepository;
import com.kartblaze.shopsy.repositories.CartItemRepository;
import com.kartblaze.shopsy.repositories.ProductRepository;
import com.kartblaze.shopsy.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addItemToCart(@RequestParam("productId") Long productId,
                                @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        AppUser user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        CartItem cartItem = new CartItem(cart, product, quantity);
        cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Invalid cart item Id:" + cartItemId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("cartItemId") Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Invalid cart item Id:" + cartItemId));
        cartItemRepository.delete(cartItem);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("cartItems", new ArrayList<>());
            model.addAttribute("totalPrice", 0);
            return "cart";
        }
        AppUser user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            model.addAttribute("cartItems", new ArrayList<>());
            model.addAttribute("totalPrice", 0);
            return "cart";
        }
        Cart cart = cartRepository.findByUser(user).orElse(null);
        List<CartItem> cartItems = (cart != null) ? cart.getItems() : new ArrayList<>();
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }
}
