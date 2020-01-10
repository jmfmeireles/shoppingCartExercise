package com.example.shoppingcartexercise.application.service;

import com.example.shoppingcartexercise.application.model.Product;
import com.example.shoppingcartexercise.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOne(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No product with id " + id));
    }

    @Transactional
    public void insert(Product product) {
        product.setId(UUID.randomUUID().toString());
        productRepository.save(product);
    }

    @Transactional
    public void update(String id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("No product with id " + id);
        }

        product.setId(id);

        productRepository.save(product);
    }

    @Transactional
    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("No product with id " + id);
        }

        productRepository.deleteById(id);
    }

}
