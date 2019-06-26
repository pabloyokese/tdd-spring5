package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
