package com.worksap.bootcamp.spring.bookstore.impl.dao;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;

public class DefaultDaoFactory implements DaoFactory {
	public DefaultDaoFactory() {}

	@Override
	public CartItemRelationDao getCartItemRelationDao() {
		return new CartItemRelationDaoImpl();
	}

	@Override
	public ItemDao getItemDao() {
		return new ItemDaoImpl();
	}

	@Override
	public StockDao getStockDao() {
		return new StockDaoImpl();
	}

	@Override
	public Transaction getTransaction() {
		return new TransactionImpl();
	}

	@Override
	public OrderHeaderDao getOrderHeaderDao() {
		return new OrderHeaderDaoImpl();
	}

	@Override
	public OrderDetailDao getOrderDetailDao() {
		return new OrderDetailDaoImpl();
	}

	@Override
	public FlashDao getFlashDao() {
		return new FlashDaoImpl();
	}
}
