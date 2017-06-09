package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;

public interface OrderHeaderDao {
	public void create(OrderHeader orderHeader) throws IOException;
	public int getSequence() throws IOException;
	public List<OrderHeader> findAll() throws IOException;
	public OrderHeader find(int orderHeaderId) throws IOException;
}
