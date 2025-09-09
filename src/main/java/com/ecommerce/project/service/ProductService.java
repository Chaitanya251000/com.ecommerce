package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getProducts();

	ProductResponse getProductByCat(Long categoryId);

	ProductResponse getByKeyword(String keyword);

	ProductDTO updateProduct(ProductDTO productDTO, Long productId);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;

}
