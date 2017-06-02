package com.worksap.bootcamp.spring.bookstore.impl.dao;

import java.util.Hashtable;

import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;

public class FlashDaoImpl implements FlashDao {
	private static final Hashtable<String, Object> store = new Hashtable<String, Object>();

	@Override
	public void put(String key, Object val) {
		store.put(key, val);
	}

	@Override
	public <T> T get(String key, Class<T> klass) {
		@SuppressWarnings("unchecked")
		T remove = (T) store.remove(key);
		return remove;
	}
}
