package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartClearer;
import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartGetter;
import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
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
	private final Transaction transaction;

	@Autowired
	public OrderServiceImpl(DaoFactory daoFactory) {
		super();
		this.orderHeaderDao = daoFactory.getOrderHeaderDao();
		this.orderDetailDao = daoFactory.getOrderDetailDao();
		this.stockDao = daoFactory.getStockDao();
		this.cartDao = daoFactory.getCartItemRelationDao();
		this.itemDao = daoFactory.getItemDao();
		this.transaction = daoFactory.getTransaction();
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
		Map<Integer, CartItem> idList = new HashMap<Integer, CartItem>();

		for (CartItem cartItem : cart.getCartItems()) {
			idList.put(cartItem.getItem().getId(), cartItem);
		}

		List<Stock> stocks = stockDao.findByItemIdWithLock(transaction, idList.keySet());
		List<StockCartItemRelation> locked = new ArrayList<StockCartItemRelation>();

		for (Stock stock : stocks) {
			locked.add(new StockCartItemRelation(stock, idList.get(stock.getItemId())));
		}

		return locked;
	}

	private List<StockCartItemRelation> checkShortage(String userId, List<StockCartItemRelation> stocks) {
		List<StockCartItemRelation> shortageList = new ArrayList<OrderServiceImpl.StockCartItemRelation>();

		for (StockCartItemRelation stockCartRelation : stocks) {
			if (stockCartRelation.stock.getStock() < stockCartRelation.cartItem.getRelation().getAmount()) {
				shortageList.add(stockCartRelation);
			}
		}

		return shortageList;
	}

	private List<StockShortage> reduceCartItemAmount(String userId, Cart cart, List<StockCartItemRelation> shortageList) throws IOException {
		List<StockShortage> result = new ArrayList<OrderService.StockShortage>();

		for (StockCartItemRelation shortage : shortageList) {
			result.add(new StockShortage(shortage.cartItem.getItem(), shortage.cartItem, shortage.stock));

			if (shortage.stock.getStock() <= 0) {
				cartDao.remove(transaction, userId, shortage.stock.getItemId());
				continue;
			}

			cartDao.updateAmount(transaction, userId, shortage.stock.getItemId(), shortage.stock.getStock());
		}

		return result;
	}

	@Override
	public void order(String userId, String name, String address) throws StockShortageException{
		checkArgumentOrder(name, address);

		try {
			transaction.begin();

			Cart cart = new CartGetter(cartDao, itemDao).getCart(transaction, userId);

			List<StockCartItemRelation> locked = getStockWithLock(cart);
			List<StockCartItemRelation> shortages = checkShortage(userId, locked);

			if (!shortages.isEmpty()) {
				List<StockShortage> stockShortages = reduceCartItemAmount(userId, cart, shortages);
				transaction.commit();
				throw new StockShortageException(stockShortages);
			}

			reduceStock(locked);
			issueOrder(locked, cart, name, address);

			new CartClearer(cartDao).clear(transaction, userId);

			transaction.commit();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch (RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}

	private void reduceStock(List<StockCartItemRelation> locked) throws IOException {
		for (StockCartItemRelation stockCartItemRelation : locked) {
			stockDao.updateStock(transaction,
					stockCartItemRelation.stock.getItemId(),
					stockCartItemRelation.stock.getStock() - stockCartItemRelation.cartItem.getRelation().getAmount());
		}
	}

	private void issueOrder(List<StockCartItemRelation> locked, Cart cart, String name, String address) throws IOException {
		int orderHeaderId = orderHeaderDao.getSequence(transaction);

		orderHeaderDao.create(transaction, new OrderHeader(orderHeaderId, cart.getTotal(), name, address));

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

		for (StockCartItemRelation stockCartItemRelation : locked) {
			orderDetails.add(new OrderDetail(orderHeaderId, stockCartItemRelation.stock.getItemId(), stockCartItemRelation.cartItem.getRelation().getAmount()));
		}

		orderDetailDao.create(transaction, orderDetails);
	}

	private void checkArgumentOrder(String name, String address) {
		// TODO Auto-generated method stub
	}
}
