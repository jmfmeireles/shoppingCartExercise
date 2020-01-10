package com.example.shoppingcartexercise;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private ProductRepository productRepo;

    @Test
    public void canCreateAndGetAProduct() throws Exception {
    	Product product = new Product("dummyId", "Test Product Name", "Test Product Description", 2.0);

    	performProductPost(product);
        
    	//get product from repository
        Product productAfterReturn = productRepo.findById("dummyId").get();
        assertEquals(productAfterReturn.getName(), "Test Product Name");
        assertEquals(productAfterReturn.getDescription(), "Test Product Description");
        assertEquals(productAfterReturn.getPrice(), 2.0, 0);
        
        //get product performing a get request
        mvc.perform(get("/products/get")
        		.param("productId", "dummyId"))
        		.andExpect(jsonPath("$.name").value("Test Product Name"))
        		.andExpect(jsonPath("$.description").value("Test Product Description"))
        		.andExpect(jsonPath("$.price").value(2.0));
        
        //delete product
        productRepo.delete(product);
    }
    
    @Test
    public void canGetAllProducts() throws Exception {
    	//first, check the actual number of products
    	int actualNumberOfProducts = productRepo.findAll().size();
    	
    	//add one product
    	Product product = new Product("", "Test Product Name", "Test Product Description", 2.0);
    	performProductPost(product);
    	
    	//get products from repository
        List<Product> products = productRepo.findAll();
        assertEquals(products.size(), actualNumberOfProducts + 1);
        
        //get products performing a get request
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/products/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        products = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<Product>>(){});
        assertEquals(actualNumberOfProducts + 1, products.size());
        
        //delete product
        productRepo.delete(product);
    	
    }

    // Private methods
    private void performProductPost(Product product) throws Exception {
    	mvc.perform(post("/products/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());mvc.perform(post("/products/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

}
