package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class ItemStock {
	private final Item item;
	private final Stock stock;

	public ItemStock(Item item, Stock stock) {
		this.item = item;
		this.stock = stock;
	}

	public Item getItem() {
		return item;
	}

	public Stock getStock() {
		return stock;
	}
}
