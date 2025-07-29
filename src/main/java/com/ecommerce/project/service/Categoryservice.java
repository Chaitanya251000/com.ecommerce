package com.ecommerce.project.service;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface Categoryservice {

	public CategoryResponse getCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	public CategoryDTO createcategory(CategoryDTO categoryDTO);

	public CategoryDTO deleteCategory(Long id);

	public CategoryDTO updateCat(CategoryDTO categoryDTO, Long categoryId);
}
