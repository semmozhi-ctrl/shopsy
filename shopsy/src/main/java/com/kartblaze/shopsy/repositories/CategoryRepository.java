package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
