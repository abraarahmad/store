package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartClearer;
import com.worksap.bootcamp.spring.bookstore.impl.services.shared.CartGetter;
import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartAddResult;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Item;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;
import com.worksap.bootcamp.spring.bookstore.spec.services.CartService;

@Component
public class CartServiceImpl implements CartService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final int CART_MAX_AMOUNT = 9;

	private final CartItemRelationDao cartDao;
	private final ItemDao itemDao;
	private final StockDao stockDao;
	private final Transaction transaction;

	@Autowired
	public CartServiceImpl(DaoFactory daoFactory) {
		this.cartDao = daoFactory.getCartItemRelationDao();
		this.itemDao = daoFactory.getItemDao();
		this.transaction = daoFactory.getTransaction();
		this.stockDao = daoFactory.getStockDao();
	}

	@Override
	public CartAddResult addCart(String userId, int itemId, int amount) {
		try {
			transaction.begin();

			Item item = itemDao.find(transaction, itemId);

			if (item == null) {
				throw new IllegalArgumentException("item_id is not exist");
			}

			CartItemRelation cartItem = cartDao.findByUserIdAndItemId(transaction, userId, itemId);
			Stock stock = stockDao.find(transaction, itemId);
			CartAddResult result = new CartLogic().calculate(cartItem, stock, amount, CART_MAX_AMOUNT);

			if (cartItem == null) {
				cartDao.create(transaction, new CartItemRelation(userId, itemId, result.getNewAmount()));
			} else {
				cartDao.updateAmount(transaction, userId, itemId, result.getNewAmount());
			}

			transaction.commit();

			return result;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch(RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}

	@Override
	public Cart getCart(String userId) {
		try {
			transaction.begin();

			Cart cart = new CartGetter(cartDao, itemDao).getCart(transaction, userId);

			transaction.commit();

			return cart;
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch(RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}

	@Override
	public void removeItem(String userId, int itemId) {
		try {
			transaction.begin();

			cartDao.remove(transaction, userId, itemId);

			transaction.commit();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch(RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}

	@Override
	public void clear(String userId) {
		try {
			transaction.begin();

			new CartClearer(cartDao).clear(transaction, userId);

			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch(RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}
}
