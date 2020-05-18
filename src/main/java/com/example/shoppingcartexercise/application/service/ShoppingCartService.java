package com.example.shoppingcartexercise.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.model.ShoppingCartItem;
import com.example.shoppingcartexercise.application.repository.ProductsRepo;
import com.example.shoppingcartexercise.application.repository.ShoppingCartRepo;

@Service
public class ShoppingCartService {

	@Autowired
	private ShoppingCartRepo shoppingCartRepo;
	
	@Autowired
	private ProductsRepo productRepo;

	@Transactional
	public void save(ShoppingCart shoppingCart) throws Exception {
		Boolean thereAreInvalidQuantities = shoppingCart.getProducts().stream().anyMatch(item -> item.getQuantity() <= 0);
		if (!thereAreInvalidQuantities) {
			if (StringUtils.isEmpty(shoppingCart.getId())) {
				shoppingCart.setId(UUID.randomUUID().toString());
			}
			shoppingCartRepo.save(shoppingCart);
		} else {
			throw new Exception("There are products with invalid quantities!");
		}
	}

	@Transactional
	public List<ShoppingCart> getCurrentShoppingCart() {
		List<ShoppingCart> listOfShoppingCarts = shoppingCartRepo.findAll().stream().filter(sc->sc.getIsActive().equals(Boolean.TRUE)).collect(Collectors.toList());
		for (ShoppingCart shoppingCart : listOfShoppingCarts) {
			//get all the products information
			shoppingCart.setProducts(getProductsForShoppingCartSorted(shoppingCart));
		}
		return listOfShoppingCarts;
	}
	
	private ArrayList<ShoppingCartItem> getProductsForShoppingCartSorted(ShoppingCart shoppingCart) {
		ArrayList<ShoppingCartItem> productsOfShoppingCart = shoppingCart.getProducts();
		//first, get the product information
		for (ShoppingCartItem product : productsOfShoppingCart) {
			product.setProduct(productRepo.findById(product.getProductId()).get());
		}
		//then, sort the products by name
		Collections.sort(productsOfShoppingCart, new NameCompare());
		return productsOfShoppingCart;
	}
}

//Class to compare Movies by name 
class NameCompare implements Comparator<ShoppingCartItem> 
{ 
 public int compare(ShoppingCartItem shoppingCart1, ShoppingCartItem shoppingCart2) 
 { 
     return shoppingCart1.getProduct().getName().compareTo(shoppingCart2.getProduct().getName()); 
 } 
}
