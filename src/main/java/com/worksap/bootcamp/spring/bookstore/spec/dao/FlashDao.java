package com.worksap.bootcamp.spring.bookstore.spec.dao;

/**
 * データを一時的に保持するクラスです
 * putされたデータは一度getされると消滅します
 */
public interface FlashDao {
	public void put(String key, Object val);
	public <T> T get(String key, Class<T> klass);
}
