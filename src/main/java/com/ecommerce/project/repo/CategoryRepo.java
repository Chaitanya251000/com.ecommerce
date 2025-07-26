package com.ecommerce.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
