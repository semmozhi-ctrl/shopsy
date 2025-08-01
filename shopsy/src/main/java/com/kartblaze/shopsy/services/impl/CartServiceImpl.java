package com.kartblaze.shopsy.services.impl;

import com.kartblaze.shopsy.entities.Cart;
import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.repositories.CartRepository;
import com.kartblaze.shopsy.services.CartService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> findByUser(AppUser user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart addProduct(AppUser user, Product product) {
        Cart cart = cartRepository.findByUser(user).orElse(new Cart(null, user, new java.util.ArrayList<>()));
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProduct(AppUser user, Product product) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            cart.getProducts().remove(product);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public void clearCart(AppUser user) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            cart.getProducts().clear();
            cartRepository.save(cart);
        }
    }
}
