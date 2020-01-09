package com.example.shoppingcartexercise.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.shoppingcartexercise.application.model.Product;

public interface ProductsRepo extends MongoRepository<Product, String> {
}
