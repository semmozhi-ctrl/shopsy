
package com.kartblaze.shopsy.services;

import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.entities.Category;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findByCategory(Category category);
    Product findById(Long id);
    Product save(Product product);
}
