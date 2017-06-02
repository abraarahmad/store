package com.worksap.bootcamp.spring.bookstore.spec.services;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartAddResult;

public interface CartService {
	public CartAddResult addCart(String userId, int itemId, int amount);
	public Cart getCart(String userId);
	public void removeItem(String userId, int itemId);
	public void clear(String userId);
}
