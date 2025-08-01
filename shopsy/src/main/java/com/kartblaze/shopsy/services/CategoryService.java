package com.kartblaze.shopsy.services;

import com.kartblaze.shopsy.entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
}
