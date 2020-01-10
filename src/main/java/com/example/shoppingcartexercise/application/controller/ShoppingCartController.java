package com.example.shoppingcartexercise.application.controller;

import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Get Service to get the active shopping carts
     *
     * @return the response with a list of active shopping carts
     * @author Joao Meireles
     */
    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAll() {
        return new ResponseEntity<>(shoppingCartService.getAll(), HttpStatus.OK);
    }

    /**
     * Post Service to create or update a shopping cart.
     * If the shopping cart has an id, the record is updated on the database. Otherwise, a new record is created
     *
     * @param shoppingCart (required) - shopping cart to be saved/updated
     * @return the response with the shopping cart that was saved/updated
     * @author Joao Meireles
     */
    @PostMapping
    public ResponseEntity<ShoppingCart> create(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.insert(shoppingCart);
        return new ResponseEntity<>(shoppingCart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> update(@RequestBody ShoppingCart shoppingCart, @PathVariable String id) {
        shoppingCartService.update(id, shoppingCart);
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

}
