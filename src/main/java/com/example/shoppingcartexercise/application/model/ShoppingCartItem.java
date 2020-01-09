package com.example.shoppingcartexercise.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Document
@AllArgsConstructor
public @Data class ShoppingCartItem {
	
	@Id
	String productId;
	@Transient
	Product product;
	@NonNull
    private Integer quantity;
}
