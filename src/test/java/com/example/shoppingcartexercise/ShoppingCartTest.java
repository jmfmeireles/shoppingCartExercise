package com.example.shoppingcartexercise;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.model.ShoppingCartItem;
import com.example.shoppingcartexercise.application.repository.ProductsRepo;
import com.example.shoppingcartexercise.application.repository.ShoppingCartRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private ShoppingCartRepo shoppingCartRepo;
	
	@Autowired
	private ProductsRepo productRepo;
	    
    
    @Test
    public void cannotCreateAShoppingCartWithoutProducts() throws Exception {
    	ArrayList<ShoppingCartItem> products = new ArrayList<ShoppingCartItem>();
    	ShoppingCart shoppingCart = new ShoppingCart("", true, products);
    	
    	try {
    		mvc.perform(post("/shoppingCart/createOrUpdate")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(shoppingCart)));
    	} catch(Exception e) {
    		assertTrue(e.getMessage().contains("This shopping cart does not have products!"));
    	}
    }
    
    @Test
    public void cannotCreateAShoppingCartWithProductsWithNegativeProducts() throws Exception {
    	ArrayList<ShoppingCartItem> products = new ArrayList<ShoppingCartItem>();
    	products.add(new ShoppingCartItem("id1", null, -1));
    	products.add(new ShoppingCartItem("id2", null, 1));
    	ShoppingCart shoppingCart = new ShoppingCart("", true, products);
    	
    	try {
    		mvc.perform(post("/shoppingCart/createOrUpdate")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(shoppingCart)));
    	} catch(Exception e) {
    		assertTrue(e.getMessage().contains("There are products with invalid quantities!"));
    	}
    }
    
    @Test
    public void createAndUpdateAShoppingCart() throws Exception{
    	//test the creation of a shopping cart; add a product with quantity equal to 1
    	ArrayList<ShoppingCartItem> products = new ArrayList<ShoppingCartItem>();
    	products.add(new ShoppingCartItem("idProduct1", null, 1));
    	ShoppingCart shoppingCart = new ShoppingCart("", true, products);
    	
    	MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.post("/shoppingCart/createOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(shoppingCart)))
                .andExpect(status().isOk())
                .andReturn();
    	    	
    	ShoppingCart shoppingCartAfterSave = new ObjectMapper().readValue(mvcResult1.getResponse().getContentAsString(), ShoppingCart.class);
    	assertFalse(StringUtils.isEmpty(shoppingCartAfterSave.getId()));
    	assertEquals(shoppingCartAfterSave.getIsActive(), true);
    	assertEquals(shoppingCartAfterSave.getProducts().get(0).getProductId(), "idProduct1");
    	assertEquals(shoppingCartAfterSave.getProducts().get(0).getQuantity(), 1, 0);
    	
    	//update the shopping cart
    	shoppingCartAfterSave.setIsActive(false);
    	shoppingCartAfterSave.getProducts().get(0).setQuantity(10);
    	shoppingCartAfterSave.getProducts().add(new ShoppingCartItem("idProduct2", null, 2));
    	MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post("/shoppingCart/createOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(shoppingCartAfterSave)))
                .andExpect(status().isOk())
                .andReturn();
    	    	
    	ShoppingCart shoppingCartUpdate = new ObjectMapper().readValue(mvcResult2.getResponse().getContentAsString(), ShoppingCart.class);
    	assertEquals(shoppingCartUpdate.getIsActive(), false);
    	assertEquals(shoppingCartUpdate.getProducts().size(), 2);
    	for (ShoppingCartItem item: shoppingCartUpdate.getProducts()) {
    		switch(item.getProductId()) {
    			case "idProduct1":
    				assertEquals(item.getQuantity(), 10, 0);
    				break;
    			case "idProduct2":
    				assertEquals(item.getQuantity(), 2, 0);
    				break;
    			default:
    				fail("Product not expected");
    		}
    	}
    	//delete the shopping cart
    	shoppingCartRepo.delete(shoppingCartAfterSave);
    }
    
    @Test
    public void checkIfProductsRetrievedAreSortedByName() throws Exception{
    	//insert three dummy products
    	Product product1 = new Product("idProduct1", "A product that must appear first", "", 2.0);
    	Product product2 = new Product("idProduct2", "Second", "", 2.0);
    	Product product3 = new Product("idProduct3", "Well, this must be the last", "", 2.0);
    	productRepo.save(product1);
    	productRepo.save(product2);
    	productRepo.save(product3);
    	
    	//create shopping cart with these three products
    	ArrayList<ShoppingCartItem> products = new ArrayList<ShoppingCartItem>();
    	products.add(new ShoppingCartItem("idProduct2", null, 1));
    	products.add(new ShoppingCartItem("idProduct1", null, 1));
    	products.add(new ShoppingCartItem("idProduct3", null, 1));
    	ShoppingCart shoppingCart = new ShoppingCart("dummyId", true, products);
    	shoppingCartRepo.save(shoppingCart);
    	
    	//get the active shopping carts
    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/shoppingCart/getActiveShoppingCarts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ShoppingCart> listOfActiveShoppingCarts = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<ShoppingCart>>(){});
        assertFalse(CollectionUtils.isEmpty(listOfActiveShoppingCarts));
        Boolean testShoppingCartWasCreated = false;
        for (ShoppingCart activeShoppingCart: listOfActiveShoppingCarts) {
        	if (shoppingCart.getId().equals("dummyId")) {
        		ArrayList<ShoppingCartItem> productsThatMustBeSorted = activeShoppingCart.getProducts();
        		assertEquals(productsThatMustBeSorted.get(0).getProductId(), "idProduct1");
        		assertEquals(productsThatMustBeSorted.get(1).getProductId(), "idProduct2");
        		assertEquals(productsThatMustBeSorted.get(2).getProductId(), "idProduct3");
        		testShoppingCartWasCreated = true;
        	}
        }
        if (!testShoppingCartWasCreated) {
        	fail("The test shopping cart was not retrieved");
        }
    	
    	//delete the dummy products and the shopping cart
    	productRepo.delete(product1);
    	productRepo.delete(product2);
    	productRepo.delete(product3);
    	shoppingCartRepo.delete(shoppingCart);
    }
    
}
