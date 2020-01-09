package com.example.shoppingcartexercise.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.repository.ProductsRepo;

@Service
public class ProductsService {

	@Autowired
	private ProductsRepo productsRepo;

	@Transactional
	public void save(Product product) {
		if (StringUtils.isEmpty(product.getId())) {
			product.setId(UUID.randomUUID().toString());
		}
		productsRepo.save(product);
	}

	@Transactional
	public List<Product> getAllProducts() {
		return productsRepo.findAll();
	}
	
	@Transactional
	public Product getProduct(String productId) {
		return productsRepo.findById(productId).get();
	}
}
