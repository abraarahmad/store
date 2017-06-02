package com.worksap.bootcamp.spring.bookstore.impl.view;

import com.worksap.bootcamp.spring.bookstore.spec.dto.ItemStock;

public class ItemListView {
	private final Iterable<ItemStock> items;

	public ItemListView(Iterable<ItemStock> items) {
		this.items = items;
	}

	public Iterable<ItemStock> getItems() {
		return items;
	}
}
