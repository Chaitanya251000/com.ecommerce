package com.ecommerce.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

	List<Product> findByCategory(Category category);

	List<Product> findByProductNameLikeIgnoreCase(String string);

}
