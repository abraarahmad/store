package com.worksap.bootcamp.spring.bookstore.impl.view;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;

public class ThanksView {
	private final Cart orderedCart;

	public ThanksView(Cart order) {
		this.orderedCart = order;
	}

	public Cart getOrderedCart() {
		return orderedCart;
	}
}
