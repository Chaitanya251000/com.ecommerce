package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	ProductResponse getProductByCat(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	ProductResponse getByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	ProductDTO updateProduct(ProductDTO productDTO, Long productId);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;

}
