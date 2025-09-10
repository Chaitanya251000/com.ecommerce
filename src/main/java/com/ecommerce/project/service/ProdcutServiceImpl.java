package com.ecommerce.project.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repo.CategoryRepo;
import com.ecommerce.project.repo.ProductRepo;

@Service
public class ProdcutServiceImpl implements ProductService{
	
	@Autowired 
	CategoryRepo catRepo;
	
	@Autowired
	ProductRepo prodRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@Override
	public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		boolean isProductNotPresent = true;
		List<Product> products = category.getProducts();
		for (Product product : products) {
			if (product.getProductName().equalsIgnoreCase(productDTO.getProductName())) {
				isProductNotPresent = false;
				break;
			}
		}
		if(isProductNotPresent) {
			Product product = modelMapper.map(productDTO, Product.class);
			product.setCategory(category);
			product.setImage("default.png");  
			double discountedPrice = product.getPrice() - (product.getDiscount()/100*product.getPrice());//100 - 25/100 *100
			product.setSpecialPrice(discountedPrice);
			Product savedProduct = prodRepo.save(product);
			ProductDTO prodDTO = modelMapper.map(savedProduct, ProductDTO.class);
			return prodDTO;
		}else {
			throw new APIException("Product already exists !!!");
		}
		
	}

	@Override
	public ProductResponse getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		//pagination and sorting 
		Sort sortByandOrder = sortDir.equalsIgnoreCase("asc")?
								Sort.by(sortBy).ascending()
								:Sort.by(sortBy).descending();
		Pageable pageDetail = PageRequest.of(pageNumber, pageSize, sortByandOrder);
		Page<Product> productPage = prodRepo.findAll(pageDetail);
		
		List<Product> products = productPage.getContent();
		List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		response.setPageNumber(productPage.getNumber());
		response.setPageSize(productPage.getSize());
		response.setTotalElements(productPage.getTotalElements());
		response.setTotalPages(productPage.getTotalPages());
		response.setLastPage(productPage.isLast());
		return response;
	}

	@Override
	public ProductResponse getProductByCat(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Category category = catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		// pagination and sorting
		Sort sortByandOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageDetail = PageRequest.of(pageNumber, pageSize, sortByandOrder);
		Page<Product> productPage = prodRepo.findByCategory(category,pageDetail);
		List<Product> products = productPage.getContent();
		
		List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		response.setPageNumber(productPage.getNumber());
		response.setPageSize(productPage.getSize());
		response.setTotalElements(productPage.getTotalElements());
		response.setTotalPages(productPage.getTotalPages());
		response.setLastPage(productPage.isLast());
		return response;
	}

	@Override
	public ProductResponse getByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// pagination and sorting
		Sort sortByandOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageDetail = PageRequest.of(pageNumber, pageSize, sortByandOrder);
		
		Page<Product> productPage = prodRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%',pageDetail);
		List<Product> products = productPage.getContent();
		
		List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		response.setPageNumber(productPage.getNumber());
		response.setPageSize(productPage.getSize());
		response.setTotalElements(productPage.getTotalElements());
		response.setTotalPages(productPage.getTotalPages());
		response.setLastPage(productPage.isLast());
		return response;
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
		
		Product existingproduct = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		
		Product product = modelMapper.map(productDTO, Product.class);
		
		existingproduct.setProductName(product.getProductName());
		existingproduct.setProductDesc(product.getProductDesc());
		existingproduct.setQuantity(product.getQuantity());
		existingproduct.setPrice(product.getPrice());
		existingproduct.setDiscount(product.getDiscount());
		double discountedPrice = product.getPrice() - (product.getDiscount()/100*product.getPrice());//100 - 25/100 *100
		existingproduct.setSpecialPrice(discountedPrice);
		
		Product savedProd = prodRepo.save(existingproduct);
		ProductDTO updatedProduct = modelMapper.map(savedProd, ProductDTO.class);
		return updatedProduct;
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product existingProd = prodRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
		
		prodRepo.delete(existingProd);
		
		ProductDTO prodDTO = modelMapper.map(existingProd, ProductDTO.class);
		return prodDTO;
	}

	@Override
	public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
		// get product from db
		Product existingProd = prodRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		// upload image to server
		// get filename of uploaded img
		String fileName = fileService.uploadImage(path,image);
		// update new file name to product and save 
		existingProd.setImage(fileName);
		Product updatedProd = prodRepo.save(existingProd);
		// return DTO
		return modelMapper.map(updatedProd, ProductDTO.class);
	}

}
