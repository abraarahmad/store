package com.worksap.bootcamp.spring.bookstore.impl.services;

import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;
import com.worksap.bootcamp.spring.bookstore.spec.services.FlashService;

public class FlashServiceImpl implements FlashService {
	private final FlashDao flashDao;

	public FlashServiceImpl(FlashDao flashDao) {
		this.flashDao = flashDao;
	}

	@Override
	public void put(String key, Object obj) {
		flashDao.put(key, obj);
	}

	@Override
	public <T> T get(String key, Class<T> klass) {
		return flashDao.get(key, klass);
	}
}
