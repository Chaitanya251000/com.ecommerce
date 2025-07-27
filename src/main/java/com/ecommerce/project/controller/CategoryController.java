package com.ecommerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.Categoryservice;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	@Autowired
	private Categoryservice categoryService;

	@GetMapping("/api/public/categories")
	// @RequestMapping(value ="/api/public/categories" , method = RequestMethod.GET)
	// OR
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/api/public/category")
	public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
		categoryService.createcategory(category);
		return new ResponseEntity<>("Category Added Successfully !!", HttpStatus.CREATED);
	}

	@DeleteMapping("/api/admin/category/{categoryId}")
	public ResponseEntity<String> removeCategory(@PathVariable Long categoryId) {

		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>("Category with categoryId : " + categoryId + " deleted successfully !!",
				HttpStatus.OK);
		// return ResponseEntity.ok(status); OR
		// return ResponseEntity.status(HttpStatus.OK).body(status); OR
	}

	@PutMapping("api/public/category/{categoryId}")
	public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {

		categoryService.updateCat(category, categoryId);
		return new ResponseEntity<>("Category with CategoryId : " + categoryId + " updated sucessfully !!",
				HttpStatus.OK);
	}
}
