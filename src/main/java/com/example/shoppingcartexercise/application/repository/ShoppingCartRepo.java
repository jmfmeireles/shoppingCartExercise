package com.example.shoppingcartexercise.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.shoppingcartexercise.application.model.ShoppingCart;

public interface ShoppingCartRepo extends MongoRepository<ShoppingCart, String> {
}
