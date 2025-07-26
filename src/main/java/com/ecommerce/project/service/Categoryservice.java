package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Category;

public interface Categoryservice {

	public List<Category> getCategories();
	
	public void createcategory(Category category);

	public void deleteCategory(Long id);

	public void updateCat(Category category, Long categoryId);
}
