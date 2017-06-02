package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class CartItemRelation {
	private final String userId;
	private final int amount;
	private final int itemId;

	public CartItemRelation(String userId, int itemId,int amount) {
		this.userId = userId;
		this.amount = amount;
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public int getAmount() {
		return amount;
	}

	public int getItemId() {
		return itemId;
	}
}
