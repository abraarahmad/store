package com.worksap.bootcamp.spring.bookstore.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;

@Component
public class DefaultDaoFactory implements DaoFactory {
	private Transaction transaction;
	
	public DefaultDaoFactory() {}

	 @Autowired
	  public  DefaultDaoFactory(Transaction transaction) {
	    this.transaction = transaction;
	  }
	    
	 
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
		return transaction;
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
