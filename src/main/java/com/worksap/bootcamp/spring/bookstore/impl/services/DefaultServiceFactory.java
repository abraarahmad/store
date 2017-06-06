package com.worksap.bootcamp.spring.bookstore.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.impl.dao.DefaultDaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.services.CartService;
import com.worksap.bootcamp.spring.bookstore.spec.services.FlashService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ItemService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ServiceFactory;

@Component
public class DefaultServiceFactory implements ServiceFactory {
	private final DaoFactory daoFactory;
	
	@Autowired
	public CartService cartService;
	@Autowired
	public ItemService itemService;
	@Autowired
	public OrderService orderService;
	@Autowired
	public ManageOrderService manageOrderService;
	@Autowired
	public FlashService flashService;

	@Autowired
	public DefaultServiceFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public DefaultServiceFactory() {
		this.daoFactory = new DefaultDaoFactory();
	}

	@Override
	public CartService getCartService() {
		return cartService;
	}

	@Override
	public ItemService getItemService() {
		return itemService;
	}

	@Override
	public OrderService getOrderService() {
		return orderService;
	}

	@Override
	public ManageOrderService getManageOrderService() {
		return manageOrderService;
	}

	@Override
	public FlashService getFlashService() {
		return flashService;
	}
}
