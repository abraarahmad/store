package com.worksap.bootcamp.spring.bookstore.spec.services;

import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;

public interface ManageOrderService {
	public List<OrderHeader> getOrderHeaders();
	public Order getOrder(int orderId);
}
