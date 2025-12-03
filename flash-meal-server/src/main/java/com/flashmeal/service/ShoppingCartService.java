package com.flashmeal.service;

import com.flashmeal.dto.ShoppingCartDTO;
import com.flashmeal.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();
}
