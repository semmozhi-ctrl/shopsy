package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.entities.Wishlist;
import com.kartblaze.shopsy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(AppUser user);
    List<Wishlist> findByProduct(Product product);
}
