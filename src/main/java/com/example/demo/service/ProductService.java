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

	public Iterable<Product> findAll(){
		return productRepository.findAll();
	}

	public Product save(Product product){
		return productRepository.save(product);
	}

	public Boolean update(Product product){
		return productRepository.save(product)!=null;
	}
}
