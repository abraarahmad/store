package com.worksap.bootcamp.spring.bookstore.impl.services.shared;

import java.io.IOException;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;

public class CartClearer {
	private final CartItemRelationDao cartDao;

	public CartClearer(CartItemRelationDao cartDao) {
		this.cartDao = cartDao;
	}

	public void clear(Transaction transaction, String userId) {
		try {
			cartDao.removeByUserId(transaction, userId);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
