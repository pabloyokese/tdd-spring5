package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository; 
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	public Optional<Product> findById(Long id){
		return productRepository.findById(id);
	}
}
