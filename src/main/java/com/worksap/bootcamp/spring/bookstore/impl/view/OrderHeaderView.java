package com.worksap.bootcamp.spring.bookstore.impl.view;

import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;

public class OrderHeaderView {
	private final List<OrderHeader> headers;

	public OrderHeaderView(List<OrderHeader> headers) {
		this.headers = headers;
	}

	public List<OrderHeader> getHeaders() {
		return headers;
	}
}
