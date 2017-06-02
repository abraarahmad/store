package com.worksap.bootcamp.spring.bookstore.impl.view;

import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;

public class CartView {
	private final Cart cart;
	private final List<String> messages;

	public CartView(Cart cart, List<String> message) {
		this.cart = cart;
		this.messages = message;
	}

	public Cart getCart() {
		return cart;
	}

	public List<String> getMessages() {
		return messages;
	}
}
