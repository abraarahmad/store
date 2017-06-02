package com.worksap.bootcamp.spring.bookstore.impl.services;

import com.worksap.bootcamp.spring.bookstore.spec.dto.CartAddResult;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;

class CartLogic {
	CartAddResult calculate(CartItemRelation cartItem, Stock stock, int amount, int maxCapacity) {
		int newAmount = cartItem != null ? cartItem.getAmount() + amount : amount;
		int overflowed = 0;
		int shortage = 0;

		if (newAmount > maxCapacity) {
			overflowed = newAmount - maxCapacity;
			newAmount = maxCapacity;
		}

		if (newAmount > stock.getStock()) {
			shortage = newAmount - stock.getStock();
			newAmount -= shortage;
		}

		int actualAdded = cartItem != null ? newAmount - cartItem.getAmount() : newAmount;

		return new CartAddResult(newAmount, overflowed, shortage, actualAdded, maxCapacity);
	}

}
