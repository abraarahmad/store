package com.worksap.bootcamp.spring.bookstore.impl.services.shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Cart;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItem;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;

public class CartGetter {
	private final CartItemRelationDao cartDao;
	private final ItemDao itemDao;

	public CartGetter(CartItemRelationDao cartDao, ItemDao itemDao) {
		this.cartDao = cartDao;
		this.itemDao = itemDao;
	}

	public Cart getCart(Transaction transaction, String userId) {
		try {
			Iterable<CartItemRelation> relations = cartDao.findByUserId(transaction, userId);
			List<CartItem> cartItems = new ArrayList<CartItem>();
			int total = 0;
			boolean valid = true;

			for (CartItemRelation relation : relations) {
				cartItems.add(new CartItem(relation, itemDao.find(transaction,
						relation.getItemId())));
			}

			for (CartItem cartItem : cartItems) {
				valid = valid && cartItem.getRelation().getAmount() > 0;
			}

			for (CartItem cartItem : cartItems) {
				total += cartItem.getItem().getPrice()
						* cartItem.getRelation().getAmount();
			}

			return new Cart(cartItems, total, valid);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
