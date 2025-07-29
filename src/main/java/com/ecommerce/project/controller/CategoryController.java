package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.Categoryservice;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	@Autowired
	private Categoryservice categoryService;

	@GetMapping("/api/public/categories")
	// @RequestMapping(value ="/api/public/categories" , method = RequestMethod.GET)
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name="sortBy",defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
			@RequestParam(name="sortDir",defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		CategoryResponse categories = categoryService.getCategories(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/api/public/category")
	public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category) {
		CategoryDTO catDTO = categoryService.createcategory(category);
		return new ResponseEntity<>(catDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/admin/category/{categoryId}")
	public ResponseEntity<CategoryDTO> removeCategory(@PathVariable Long categoryId) {

		CategoryDTO catDTO = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(catDTO,HttpStatus.OK);
		// return ResponseEntity.ok(status); OR
		// return ResponseEntity.status(HttpStatus.OK).body(status); OR
	}

	@PutMapping("api/public/category/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

		CategoryDTO catDTO = categoryService.updateCat(categoryDTO, categoryId);
		return new ResponseEntity<>(catDTO,HttpStatus.OK);
	}
}
