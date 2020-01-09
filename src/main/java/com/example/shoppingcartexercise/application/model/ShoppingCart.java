package com.example.shoppingcartexercise.application.model;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection="shoppingcart")
@AllArgsConstructor
public @Data class ShoppingCart {
	
	@Id
	private String id;
	private Boolean isActive;
	private ArrayList<ShoppingCartItem> products;
}
