package com.example.shoppingcartexercise.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.shoppingcartexercise.application.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
