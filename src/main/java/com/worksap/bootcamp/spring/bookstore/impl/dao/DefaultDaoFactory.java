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
	@Autowired
	private CartItemRelationDao cartItemRelationDao;
	@Autowired
	private ItemDaoImpl itemDaoImpl;
	@Autowired
	private StockDaoImpl stockDaoImpl;
	@Autowired
	private OrderHeaderDaoImpl orderHeaderDaoImpl;
	@Autowired
	private OrderDetailDaoImpl orderDetailDaoImpl;
	@Autowired
	private FlashDaoImpl flashDaoImpl;
	
	
	public DefaultDaoFactory() {}

//	 @Autowired
//	  public  DefaultDaoFactory(Transaction transaction) {
//	    this.transaction = transaction;
//	  }
	    
	 
	@Override
	public CartItemRelationDao getCartItemRelationDao() {
		return cartItemRelationDao;
	}

	@Override
	public ItemDao getItemDao() {
		return itemDaoImpl;
	}

	@Override
	public StockDao getStockDao() {
		return stockDaoImpl;
	}

//	@Override
//	public Transaction getTransaction() {
//		return transaction;
//	}

	@Override
	public OrderHeaderDao getOrderHeaderDao() {
		return orderHeaderDaoImpl;
	}

	@Override
	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDaoImpl;
	}

	@Override
	public FlashDao getFlashDao() {
		return flashDaoImpl;
	}
}
