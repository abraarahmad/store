package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;

public interface CartItemRelationDao {
	public CartItemRelation findByUserIdAndItemId(String userId, int itemId) throws IOException;
	public void create(CartItemRelation item) throws IOException;
	public void updateAmount(String userId, int itemId, int newAmount) throws IOException;
	public List<CartItemRelation> findByUserId(String userId) throws IOException;
	public void remove(String userId, int itemId) throws IOException;
	public void removeByUserId(String userId) throws IOException;
}
