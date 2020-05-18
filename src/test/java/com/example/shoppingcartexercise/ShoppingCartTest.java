package com.example.shoppingcartexercise;

import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.repository.ShoppingCartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private ShoppingCartRepository shoppingCartRepo;

    @Test
    public void cannotCreateAShoppingCartWithoutProducts() throws Exception {
    	ShoppingCart shoppingCart = new ShoppingCart("", true, null);
    	
    	mvc.perform(post("/shoppingCart/createOrUpdate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(shoppingCart)))
                .andExpect(status().isInternalServerError());
    	
//
//    	performProductPost(product);
//        
//    	//get product from repository
//        Product productAfterReturn = productRepo.findById("dummyId").get();
//        assertEquals(productAfterReturn.getName(), "Test Product Name");
//        assertEquals(productAfterReturn.getDescription(), "Test Product Description");
//        assertEquals(productAfterReturn.getPrice(), 2.0, 0);
//        
//        //get product performing a get request
//        mvc.perform(get("/products/get")
//        		.param("productId", "dummyId"))
//        		.andExpect(jsonPath("$.name").value("Test Product Name"))
//        		.andExpect(jsonPath("$.description").value("Test Product Description"))
//        		.andExpect(jsonPath("$.price").value(2.0));
//        
//        //delete product
//        productRepo.delete(product);
    }

}
