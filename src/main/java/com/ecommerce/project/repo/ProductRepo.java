package com.ecommerce.project.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

	Page<Product> findByCategory(Category category, Pageable pageDetail);

	Page<Product> findByProductNameLikeIgnoreCase(String string, Pageable pageDetail);

}
