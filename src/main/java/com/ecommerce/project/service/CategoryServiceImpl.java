package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements Categoryservice {

	@Autowired
	private CategoryRepo repo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponse getCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		//pagination and sorting
		Sort sortCategory = sortDir.equalsIgnoreCase("asc") ? 
							Sort.by(sortBy).ascending()
							: Sort.by(sortBy).descending();		
		Pageable pageDetail = PageRequest.of(pageNumber, pageSize,sortCategory);
		Page<Category> categoryPage = repo.findAll(pageDetail);
		List<Category> categories = categoryPage.getContent();
		
		if (categories.isEmpty()) {
			throw new APIException("No Category created till now !!!");
		}
		List<CategoryDTO> categoryDTOList = categories.stream().map(c -> modelMapper.map(c, CategoryDTO.class))
				.toList();
		CategoryResponse response = new CategoryResponse();
		response.setContent(categoryDTOList);
		response.setPageNumber(categoryPage.getNumber());
		response.setPageSize(categoryPage.getSize());
		response.setTotalElements(categoryPage.getTotalElements());
		response.setTotalPages(categoryPage.getTotalPages());
		response.setLastPage(categoryPage.isLast());
		return response;
	}

	@Override
	public CategoryDTO createcategory(CategoryDTO categoryDTO) {

		Category category = modelMapper.map(categoryDTO, Category.class);

		Category existingCat = repo.findByCategoryName(category.getCategoryName());
		if (existingCat != null) {
			throw new APIException(
					"Category with category name : " + category.getCategoryName() + " already exists !!!");
		}

		Category savedCategory = repo.save(category);
		CategoryDTO catDTO = modelMapper.map(savedCategory, CategoryDTO.class);
		return catDTO;
	}

	@Override
	public CategoryDTO deleteCategory(Long id) {

		Category existingcat = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
		repo.delete(existingcat);

		return modelMapper.map(existingcat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCat(CategoryDTO categoryDTO, Long categoryId) {

		Category existingCat = repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Category category = modelMapper.map(categoryDTO, Category.class);
		existingCat.setCategoryName(category.getCategoryName());
		Category savedcat = repo.save(existingCat);

		return modelMapper.map(savedcat, CategoryDTO.class);
	}
}
