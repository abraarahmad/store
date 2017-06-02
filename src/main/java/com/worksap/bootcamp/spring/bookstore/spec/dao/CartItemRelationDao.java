package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;

public interface CartItemRelationDao {
	public CartItemRelation findByUserIdAndItemId(Transaction transaction, String userId, int itemId) throws IOException;
	public void create(Transaction transaction, CartItemRelation item) throws IOException;
	public void updateAmount(Transaction transaction, String userId, int itemId, int newAmount) throws IOException;
	public List<CartItemRelation> findByUserId(Transaction transaction, String userId) throws IOException;
	public void remove(Transaction transaction, String userId, int itemId) throws IOException;
	public void removeByUserId(Transaction transaction, String userId) throws IOException;
}
