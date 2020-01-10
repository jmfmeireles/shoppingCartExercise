package com.example.shoppingcartexercise.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "shoppingcart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 5812294354653040174L;

    @JsonProperty("id")
    @Id
    private String id;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("products")
    private List<ShoppingCartItem> products;

}
