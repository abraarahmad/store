package com.worksap.bootcamp.spring.bookstore.impl.view;

import java.util.Map;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Customer;

public class OrderView {
	private final Cart cart;
	private final Customer customer;
	private final Map<String, String> messages;

	public OrderView(Cart cart, Customer customer, Map<String, String> messages) {
		this.cart = cart;
		this.customer = customer;
		this.messages = messages;
	}

	public Cart getCart() {
		return cart;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Map<String, String> getMessages() {
		return messages;
	}
}
