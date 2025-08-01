package com.kartblaze.shopsy.services;

import com.kartblaze.shopsy.entities.Cart;
import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.entities.Product;
import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> findByUser(AppUser user);
    Cart addProduct(AppUser user, Product product);
    Cart removeProduct(AppUser user, Product product);
    void clearCart(AppUser user);
}
