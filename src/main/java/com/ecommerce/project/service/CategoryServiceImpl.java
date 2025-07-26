package com.ecommerce.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements Categoryservice{

	/* List<Category> categories = new ArrayList<Category>(); */
	/* Long nextId = 1L; */
	
	@Autowired
	private CategoryRepo repo;
	
	@Override
	public List<Category> getCategories() {
		return repo.findAll();
	}

	@Override
	public void createcategory(Category category) {
		/* category.setCategoryId(nextId++); */
		repo.save(category);
	}

	@Override
	public void deleteCategory(Long id) {
		//Optimized code
		Category existingcat = repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
		repo.delete(existingcat);
		/*
		 * //normal code 
		 * List<Category> categories = repo.findAll();
		 * 
		 * Category category = categories.stream().filter(c ->
		 * c.getCategoryId().equals(id)) .findFirst() .orElseThrow(() -> new
		 * ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
		 * if(category == null) { return "Category not found !!"; }
		 * repo.delete(category); return "Category with categoryId : " +
		 * category.getCategoryId() + " deleted successfully !!";
		 */
	}

	@Override
	public void updateCat(Category category, Long categoryId) {
		
		//optimized code
		Category existingCat = repo.findById(categoryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));	
		existingCat.setCategoryName(category.getCategoryName());
		repo.save(existingCat);
		
		/*
		 * //normal code
		 *  List<Category> categories = repo.findAll();
		 * 
		 * Optional<Category> cat = categories.stream() .filter(c ->
		 * c.getCategoryId().equals(categoryId)) .findFirst(); if(cat.isPresent()) {
		 * Category exisitngCategory = cat.get();
		 * exisitngCategory.setCategoryName(category.getCategoryName());
		 * repo.save(exisitngCategory); }else { throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND,"Catgory not found !!"); }
		 */
		
	}

}
