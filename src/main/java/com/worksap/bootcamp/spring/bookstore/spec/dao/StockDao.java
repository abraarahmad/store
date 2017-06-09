package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;

public interface StockDao {
	public List<Stock> getAllOrderedByItemId() throws IOException;
	public List<Stock> findByItemIdWithLock(Set<Integer> idList) throws IOException;
	public void updateStock( int itemId, int newStock) throws IOException;
	public Stock find(int itemId) throws IOException;
}
