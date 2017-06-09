package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;

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
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartAddResult;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Item;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;
import com.worksap.bootcamp.spring.bookstore.spec.services.CartService;

@Component
public class CartServiceImpl implements CartService {
	// private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final int CART_MAX_AMOUNT = 9;

	private final CartItemRelationDao cartDao;
	private final ItemDao itemDao;
	private final StockDao stockDao;

	// private final Transaction transaction;

	@Autowired
	public CartServiceImpl(DaoFactory daoFactory) {
		this.cartDao = daoFactory.getCartItemRelationDao();
		this.itemDao = daoFactory.getItemDao();
		// this.transaction = daoFactory.getTransaction();
		this.stockDao = daoFactory.getStockDao();
	}

	@Transactional
	@Override
	public CartAddResult addCart(String userId, int itemId, int amount) {
		try {
			// transaction.begin();

			Item item = itemDao.find(itemId);

			if (item == null) {
				throw new IllegalArgumentException("item_id is not exist");
			}

			CartItemRelation cartItem = cartDao.findByUserIdAndItemId(userId,
					itemId);
			Stock stock = stockDao.find(itemId);
			CartAddResult result = new CartLogic().calculate(cartItem, stock,
					amount, CART_MAX_AMOUNT);

			if (cartItem == null) {
				cartDao.create(new CartItemRelation(userId, itemId, result
						.getNewAmount()));
			} else {
				cartDao.updateAmount(userId, itemId, result.getNewAmount());
			}

			// transaction.commit();

			return result;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Transactional
	@Override
	public Cart getCart(String userId) {

		// transaction.begin();

		Cart cart = new CartGetter(cartDao, itemDao).getCart(userId);

		// transaction.commit();

		return cart;

	}

	@Transactional
	@Override
	public void removeItem(String userId, int itemId) {
		try {
			// transaction.begin();

			cartDao.remove(userId, itemId);

			// transaction.commit();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Transactional
	@Override
	public void clear(String userId) {

		// transaction.begin();

		new CartClearer(cartDao).clear(userId);

		// transaction.commit();

	}
}
