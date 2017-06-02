package com.worksap.bootcamp.spring.bookstore.spec.services;

public interface FlashService {
	public void put(String key, Object obj);
	public <T> T get(String key, Class<T> klass);
}
