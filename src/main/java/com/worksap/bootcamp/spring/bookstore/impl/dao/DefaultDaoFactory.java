package com.worksap.bootcamp.spring.bookstore.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;


@Component
public class DefaultDaoFactory implements DaoFactory {
	//private Transaction transaction;
	@Autowired
	private JdbcTemplate template;
	
	public DefaultDaoFactory() {}

//	 @Autowired
//	  public  DefaultDaoFactory(Transaction transaction) {
//	    this.transaction = transaction;
//	  }
	    
	 
	@Override
	public CartItemRelationDao getCartItemRelationDao() {
		return new CartItemRelationDaoImpl(template);
	}

	@Override
	public ItemDao getItemDao() {
		return new ItemDaoImpl(template);
	}

	@Override
	public StockDao getStockDao() {
		return new StockDaoImpl(template);
	}

//	@Override
//	public Transaction getTransaction() {
//		return transaction;
//	}

	@Override
	public OrderHeaderDao getOrderHeaderDao() {
		return new OrderHeaderDaoImpl(template);
	}

	@Override
	public OrderDetailDao getOrderDetailDao() {
		return new OrderDetailDaoImpl(template);
	}

	@Override
	public FlashDao getFlashDao() {
		return new FlashDaoImpl();
	}
}
