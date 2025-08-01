package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kartblaze.shopsy.entities.AppUser;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(AppUser user);
}