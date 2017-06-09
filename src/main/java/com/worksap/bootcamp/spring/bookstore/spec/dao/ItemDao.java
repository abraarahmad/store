package com.worksap.bootcamp.spring.bookstore.spec.dao;

import java.io.IOException;
import java.util.List;

import com.worksap.bootcamp.spring.bookstore.spec.dto.Item;

public interface ItemDao {
	public Item find(int id) throws IOException;
	public List<Item> getAllOrderdById() throws IOException;
}
