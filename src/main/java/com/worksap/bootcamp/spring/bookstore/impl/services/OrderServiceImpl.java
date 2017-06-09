package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartClearer;
import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartGetter;
import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItem;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderDetail;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;
import com.worksap.bootcamp.spring.bookstore.spec.services.OrderService;

@Component
public class OrderServiceImpl implements OrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final OrderHeaderDao orderHeaderDao;
	private final OrderDetailDao orderDetailDao;
	private final StockDao stockDao;
	private final CartItemRelationDao cartDao;
	private final ItemDao itemDao;
	//private final Transaction transaction;

	@Autowired
	public OrderServiceImpl(DaoFactory daoFactory) {
		super();
		this.orderHeaderDao = daoFactory.getOrderHeaderDao();
		this.orderDetailDao = daoFactory.getOrderDetailDao();
		this.stockDao = daoFactory.getStockDao();
		this.cartDao = daoFactory.getCartItemRelationDao();
		this.itemDao = daoFactory.getItemDao();
		//this.transaction = daoFactory.getTransaction();
	}

	private static class StockCartItemRelation {
		private final Stock stock;
		private final CartItem cartItem;

		public StockCartItemRelation(Stock stock, CartItem cartItem) {
			this.stock = stock;
			this.cartItem = cartItem;
		}
	}

	private List<StockCartItemRelation> getStockWithLock(Cart cart) throws IOException {
		
		Map<Integer, CartItem> idList = cart.getCartItems().stream()
                .collect(Collectors.toMap(
                        cartItem -> cartItem.getItem().getId(),
                        cartItem -> cartItem
                ));
        List<Stock> stocks = stockDao.findByItemIdWithLock(idList.keySet());
        List<StockCartItemRelation> locked = stocks.stream()
                .map(stock -> new StockCartItemRelation(stock, idList.get(stock.getItemId())))
                .collect(Collectors.toList());
        return locked;
	}

	private List<StockCartItemRelation> checkShortage(String userId, List<StockCartItemRelation> stocks) {
        List<StockCartItemRelation> shortageList = stocks.stream()
                .filter(scr -> scr.stock.getStock() < scr.cartItem.getRelation().getAmount())
                .collect(Collectors.toList());
        return shortageList;
	}

	
	private List<StockShortage> reduceCartItemAmount(String userId, Cart cart, List<StockCartItemRelation> shortageList) throws IOException {
		List<StockShortage> result = new ArrayList<OrderService.StockShortage>();

		for (StockCartItemRelation shortage : shortageList) {
			result.add(new StockShortage(shortage.cartItem.getItem(), shortage.cartItem, shortage.stock));

			if (shortage.stock.getStock() <= 0) {
				cartDao.remove(userId, shortage.stock.getItemId());
				continue;
			}

			cartDao.updateAmount(userId, shortage.stock.getItemId(), shortage.stock.getStock());
		}

		return result;
	}

	
	@Transactional
	@Override
	public void order(String userId, String name, String address) throws StockShortageException{
		checkArgumentOrder(name, address);

		try {
			//transaction.begin();

			Cart cart = new CartGetter(cartDao, itemDao).getCart(userId);

			List<StockCartItemRelation> locked = getStockWithLock(cart);
			List<StockCartItemRelation> shortages = checkShortage(userId, locked);

			if (!shortages.isEmpty()) {
				List<StockShortage> stockShortages = reduceCartItemAmount(userId, cart, shortages);
				//transaction.commit();
				throw new StockShortageException(stockShortages);
			}

			reduceStock(locked);
			issueOrder(locked, cart, name, address);

			new CartClearer(cartDao).clear(userId);

			//transaction.commit();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} 
	}

	private void reduceStock(List<StockCartItemRelation> locked) throws IOException {
		 try {
             locked.forEach(stockCartItemRelation -> {
                     try {
                             stockDao.updateStock(stockCartItemRelation.stock.getItemId(),
                                             stockCartItemRelation.stock.getStock() - stockCartItemRelation.cartItem.getRelation().getAmount());
                     } catch (IOException e) {
                             throw new UncheckedIOException(e);
                     }
             });
     } catch (UncheckedIOException e) {
             throw e.getCause();
     }
	}

	private void issueOrder(List<StockCartItemRelation> locked, Cart cart, String name, String address) throws IOException {
		int orderHeaderId = orderHeaderDao.getSequence();

        orderHeaderDao.create(new OrderHeader(orderHeaderId, cart.getTotal(), name, address));

        List<OrderDetail> orderDetails = locked.stream()
                        .map(stockCartItemRelation -> new OrderDetail(orderHeaderId, stockCartItemRelation.stock.getItemId(), stockCartItemRelation.cartItem.getRelation().getAmount()))
                        .collect(Collectors.toList());

        orderDetailDao.create(orderDetails);
	}

	private void checkArgumentOrder(String name, String address) {
		// TODO Auto-generated method stub
	}
}
