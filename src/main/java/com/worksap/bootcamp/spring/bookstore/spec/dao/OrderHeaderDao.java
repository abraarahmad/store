package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;

public interface OrderHeaderDao {
	public void create(Transaction transaction, OrderHeader orderHeader) throws IOException;
	public int getSequence(Transaction transaction) throws IOException;
	public List<OrderHeader> findAll(Transaction transaction) throws IOException;
	public OrderHeader find(Transaction transaction, int orderHeaderId) throws IOException;
}
