package com.worksap.bootcamp.spring.bookstore.impl.view;

import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService.StockShortage;

public class StockShortageView {
	private final List<StockShortage> stockShortages;

	public StockShortageView(List<StockShortage> stockShortage) {
		this.stockShortages = stockShortage;
	}

	public List<StockShortage> getStockShortages() {
		return stockShortages;
	}
}
