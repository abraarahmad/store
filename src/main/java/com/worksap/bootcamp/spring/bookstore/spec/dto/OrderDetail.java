package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class OrderDetail {
	private final int orderHeaderId;
	private final int itemId;
	private final int amount;

	public OrderDetail(int orderHeaderId, int itemId, int amount) {
		this.orderHeaderId = orderHeaderId;
		this.itemId = itemId;
		this.amount = amount;
	}

	public int getOrderHeaderId() {
		return orderHeaderId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getAmount() {
		return amount;
	}
}
