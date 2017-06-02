package com.worksap.bootcamp.spring.bookstore.impl.view;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;

public class OrderDetailView {
	private final Order order;

	public OrderDetailView(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}
}
