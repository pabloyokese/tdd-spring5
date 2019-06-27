package com.example.demo.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import com.example.demo.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ProductService;

@RestController
public class ProductController {
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Return the prodcuct with especific ID
	 * @param id the i
	 * @return
	 */
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Long id){		
		return productService.findById(id).map(
				product ->{
					try{
						return ResponseEntity
								.ok()
								.eTag(Integer.toString(product.getVersion()))
								.location(new URI("product/" + product.getId()))
								.body(product);
					}catch(URISyntaxException e){
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
					}

				}
				).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Return all product in the database
	 * @return
	 */
	@GetMapping("/products")
	public Iterable<Product> getProducts(){
		return productService.findAll();
	}

	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productService.save(product);

		try{
			return ResponseEntity
					.created(new URI("product/" + newProduct.getId()))
					.eTag(Integer.toString(newProduct.getVersion()))
					.body(newProduct);
		}catch(URISyntaxException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product product,
										   @PathVariable Long id,
										   @RequestHeader("If-Match") Integer ifMatch){
		Optional<Product> existingProduct = productService.findById(id);
		return existingProduct.map(p -> {
			// compare the e tags
			if(!p.getVersion().equals(ifMatch)){
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			// update product
			p.setName(product.getName());
			p.setQuantity(product.getQuantity());
			p.setVersion(p.getVersion()+1);

			try{
				if(productService.update(p)){
					return ResponseEntity
							.ok()
							.eTag(Integer.toString(product.getVersion()))
							.location(new URI("product/" + product.getId()))
							.body(product);
				}else{
					return ResponseEntity.notFound().build();
				}
			}catch (URISyntaxException e){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}).orElse(ResponseEntity.notFound().build());
	}
}
