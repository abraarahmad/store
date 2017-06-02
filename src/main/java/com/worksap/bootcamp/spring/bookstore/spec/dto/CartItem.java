package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class CartItem {
	private final CartItemRelation relation;
	private final Item item;

	public CartItem(CartItemRelation relation, Item item) {
		this.relation = relation;
		this.item = item;
	}

	public CartItemRelation getRelation() {
		return relation;
	}

	public Item getItem() {
		return item;
	}
}
