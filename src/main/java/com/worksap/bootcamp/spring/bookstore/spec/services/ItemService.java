package com.worksap.bootcamp.spring.bookstore.spec.services;

import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.ItemStock;

public interface ItemService {
	public List<ItemStock> getOnSale();
}
