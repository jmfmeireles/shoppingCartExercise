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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.service.ProductsService;



@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired 
	private ProductsService productsService;
	
	/**
	* Post Service to create a product.
	* 
	* @param product (required) - product to be saved
	* @return the response with the product that was saved
	* @author Joao Meireles
	*/
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> createNewProduct(HttpSession httpSession, @RequestBody(required=true) Product product){
		HttpHeaders httpHeaders = new HttpHeaders();
		productsService.save(product);
		return new ResponseEntity<>(product, httpHeaders, HttpStatus.OK);
	}
	
	/**
	* Get Service to get a product by id
	* 
	* @param productId (required) - id of the product to be retrieved
	* @return the response with the required product
	* @author Joao Meireles
	*/
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProduct(HttpSession httpSession, @RequestParam(required=true) String productId){
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(productsService.getProduct(productId), httpHeaders, HttpStatus.OK);
	}
	
	/**
	* Get Service to get all the products stored on the database
	* 
	* @return the response with a list of products
	* @author Joao Meireles
	*/
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProducts(HttpSession httpSession){
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(productsService.getAllProducts(), httpHeaders, HttpStatus.OK);
	}
}
