package com.example.shoppingcartexercise.application.service;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.model.ShoppingCart;
import com.example.shoppingcartexercise.application.model.ShoppingCartItem;
import com.example.shoppingcartexercise.application.repository.ProductRepository;
import com.example.shoppingcartexercise.application.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll()
                .stream()
                .filter(shoppingCart -> !shoppingCart.isActive())
                .map(shoppingCart -> {
                    shoppingCart.setProducts(getShoppingCartItemsSortedByProductName(shoppingCart));
                    return shoppingCart;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void insert(ShoppingCart shoppingCart) {
        if (shoppingCart.getProducts().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        if (shoppingCart.getProducts().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new RuntimeException("Shopping cart has one or more products with an invalid quantity");
        }

        shoppingCart.setId(UUID.randomUUID().toString());

        shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void update(String id, ShoppingCart shoppingCart) {
        if (!shoppingCartRepository.existsById(id)) {
            throw new NoSuchElementException("No shopping cart with id " + id);
        }

        if (shoppingCart.getProducts().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        if (shoppingCart.getProducts().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new RuntimeException("Shopping cart has one or more products with an invalid quantity");
        }

        shoppingCartRepository.save(shoppingCart);
    }

    // Private methods
    private List<ShoppingCartItem> getShoppingCartItemsSortedByProductName(ShoppingCart shoppingCart) {
        // Compare shopping cart items by name
        final Comparator<ShoppingCartItem> sortByName = Comparator.comparing((ShoppingCartItem item) -> item.getProduct().getName());

        return shoppingCart.getProducts()
                .stream()
                .map(shoppingCartItem -> {
                    final Product product = productRepository.findById(shoppingCartItem.getProductId())
                            .orElseThrow(() -> new NoSuchElementException("No product with id " + shoppingCartItem.getProductId()));

                    shoppingCartItem.setProduct(product);

                    return shoppingCartItem;
                })
                .sorted(sortByName)
                .collect(Collectors.toList());
    }

}