package com.example.demo.web;

import java.net.URISyntaxException;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductService;

@RestController
public class ProductController {
	ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Long id){		
		productService.findById(id).map(
				product ->{
					return ResponseEntity.notFound().build();
				});
		return null;
//				.map(						
//				product->{					
//					try {
//						return ResponseEntity
//								.ok()
//								.eTag(Integer.toString(product.getVersion()))
//								.body(product);
//					}catch(URISyntaxException e) {
//						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//					}					
//				}).orElse(ResponseEntity.notFound().build());
	}
}
