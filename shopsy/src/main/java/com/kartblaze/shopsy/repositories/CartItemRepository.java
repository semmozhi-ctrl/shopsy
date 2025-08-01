package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
