package com.example.shoppingcartexercise.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.shoppingcartexercise.application.model.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {


}
