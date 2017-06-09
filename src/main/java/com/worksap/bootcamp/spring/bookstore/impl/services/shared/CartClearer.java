package com.worksap.bootcamp.spring.bookstore.impl.services.shared;

import java.io.IOException;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;


public class CartClearer {
	private final CartItemRelationDao cartDao;

	public CartClearer(CartItemRelationDao cartDao) {
		this.cartDao = cartDao;
	}

	public void clear(String userId) {
		try {
			cartDao.removeByUserId(userId);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
