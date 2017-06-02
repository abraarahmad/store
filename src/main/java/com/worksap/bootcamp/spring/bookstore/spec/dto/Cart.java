package com.worksap.bootcamp.spring.bookstore.spec.dto;

import java.util.List;

public class Cart {
	private final List<CartItem> cartItems;
	private final int total;
	private final boolean isValid;

	public Cart(List<CartItem> cartItems, int total, boolean isValid) {
		this.cartItems = cartItems;
		this.total = total;
		this.isValid = isValid;
	}

	public Iterable<CartItem> getCartItems() {
		return cartItems;
	}

	public int getTotal() {
		return total;
	}

	public boolean isValid() {
		return isValid;
	}
}
