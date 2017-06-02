package com.worksap.bootcamp.spring.bookstore.impl.services;

import com.worksap.bootcamp.spring.bookstore.impl.dao.DefaultDaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.services.CartService;
import com.worksap.bootcamp.spring.bookstore.spec.services.FlashService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ItemService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService;
import com.worksap.bootcamp.spring.bookstore.spec.services.ServiceFactory;

public class DefaultServiceFactory implements ServiceFactory {
	private final DaoFactory daoFactory;

	public DefaultServiceFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public DefaultServiceFactory() {
		this.daoFactory = new DefaultDaoFactory();
	}

	@Override
	public CartService getCartService() {
		return new CartServiceImpl(daoFactory.getTransaction(),
				daoFactory.getCartItemRelationDao(), daoFactory.getItemDao(), daoFactory.getStockDao());
	}

	@Override
	public ItemService getItemService() {
		return new ItemServiceImpl(daoFactory.getTransaction(),
				daoFactory.getItemDao(), daoFactory.getStockDao());
	}

	@Override
	public OrderService getOrderService() {
		return new OrderServiceImpl(daoFactory.getOrderHeaderDao(),
				daoFactory.getOrderDetailDao(), daoFactory.getStockDao(),
				daoFactory.getCartItemRelationDao(), daoFactory.getItemDao(),
				daoFactory.getTransaction());
	}

	@Override
	public ManageOrderService getManageOrderService() {
		return new ManageOrderServiceImpl(daoFactory.getTransaction(), daoFactory.getOrderHeaderDao(), daoFactory.getOrderDetailDao());
	}

	@Override
	public FlashService getFlashService() {
		return new FlashServiceImpl(daoFactory.getFlashDao());
	}
}
