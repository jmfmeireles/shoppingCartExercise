package com.example.shoppingcartexercise.application.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.service.ShoppingCartService;


@RestController
@RequestMapping(value="/shoppingCart")
public class ShoppingCartController {
	
	@Autowired 
	private ShoppingCartService shoppingCartService;
	
	/**
	* Post Service to create or update a shopping cart.
	* If the shopping cart has an id, the record is updated on the database. Otherwise, a new record is created
	* 
	* @param shoppingCart (required) - shopping cart to be saved/updated
	* @return the response with the shopping cart that was saved/updated
	* @throws run time exceptions if the shopping cart does not have items OR if at least one of the items have a quantity <= 0 
	* @author Joao Meireles
	*/
	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShoppingCart> createNewShoppingCart(HttpSession httpSession, @RequestBody(required=true) ShoppingCart shoppingCart) throws Exception{
		HttpHeaders httpHeaders = new HttpHeaders();
		if (!shoppingCart.getProducts().isEmpty()) {
			shoppingCartService.save(shoppingCart);
		} else {
			throw new Exception("This shopping cart does not have products!");
		}
		return new ResponseEntity<>(shoppingCart, httpHeaders, HttpStatus.OK);
	}
	
	/**
	* Get Service to get the active shopping carts
	* 
	* @return the response with a list of active shopping carts 
	* @author Joao Meireles
	*/
	@RequestMapping(value = "/getActiveShoppingCarts", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ShoppingCart>> getActiveShoppingCarts(HttpSession httpSession){
		HttpHeaders httpHeaders = new HttpHeaders();
		List<ShoppingCart> shoppingCart = shoppingCartService.getCurrentShoppingCart();
		return new ResponseEntity<>(shoppingCart, httpHeaders, HttpStatus.OK);
	}
	
	
}

