package com.example.shoppingcartexercise.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection="product")
@AllArgsConstructor
public @Data class Product {
	
	@Id
	private String id;
	private String name;
	private String description;
	private double price;
	
}
