package com.example.demo.service;

import java.util.List;
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

	public List<Product> findAll(){
		return productRepository.findAll();
	}

	public Product save(Product product){
		product.setVersion(1);
		return productRepository.save(product);
	}

	public Boolean update(Product product){
		return productRepository.save(product)!=null;
	}

	public Boolean delete(Long id){
		boolean success;
		try{
			productRepository.deleteById(id);
			success = true;
		} catch (Exception e){
			success = false;
		}
		return success;
	}
}
