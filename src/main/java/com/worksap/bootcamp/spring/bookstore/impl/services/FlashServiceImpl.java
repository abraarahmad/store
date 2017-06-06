package com.worksap.bootcamp.spring.bookstore.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.FlashDao;
import com.worksap.bootcamp.spring.bookstore.spec.services.FlashService;

@Component
public class FlashServiceImpl implements FlashService {
	private final FlashDao flashDao;

	@Autowired
	public FlashServiceImpl(DaoFactory daoFactory) {
		this.flashDao = daoFactory.getFlashDao();
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
