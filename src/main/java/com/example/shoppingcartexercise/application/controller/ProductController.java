package com.example.shoppingcartexercise.application.controller;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Get all products persisted on database
     *
     * @return Response with a list of products
     * @author Joao Meireles
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    /**
     * Get one product persisted on database given its id
     *
     * @param id (required) - id of the product to be retrieved
     * @return Response with the required product
     * @author Joao Meireles
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getOne(@PathVariable String id) {
        return new ResponseEntity<>(productService.getOne(id), HttpStatus.OK);
    }

    /**
     * Create a product and persist on database
     *
     * @param product - product to be saved
     * @return Response with the product that was saved
     * @author Joao Meireles
     */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        productService.insert(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * Update a product persisted on database
     *
     * @param product - product to be saved
     * @param id - product id
     * @return Response with the product that was saved
     * @author Joao Meireles
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable String id) {
        productService.update(id, product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Delete a product persisted on database
     *
     * @param id - product id
     * @return Response with no content
     * @author Joao Meireles
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
