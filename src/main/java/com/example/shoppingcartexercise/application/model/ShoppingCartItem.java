package com.example.shoppingcartexercise.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Document
public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = -4528606118377812500L;

    @JsonProperty("product_id")
    @Id
    private String productId;

    @JsonProperty("product")
    private Product product;

    @JsonProperty("quantity")
    @NotNull
    private Integer quantity;

}
