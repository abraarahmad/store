package com.worksap.bootcamp.spring.bookstore.spec.dto;

import java.util.List;

public class Order {
	private final OrderHeader header;
	private final List<OrderDetail> detail;

	public Order(OrderHeader header, List<OrderDetail> detail) {
		this.header = header;
		this.detail = detail;
	}

	public OrderHeader getHeader() {
		return header;
	}

	public List<OrderDetail> getDetail() {
		return detail;
	}
}
