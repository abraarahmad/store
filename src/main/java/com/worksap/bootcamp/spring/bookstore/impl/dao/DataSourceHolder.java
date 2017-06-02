package com.worksap.bootcamp.spring.bookstore.impl.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

final class DataSourceHolder {
	private final DataSource ds;

	public DataSourceHolder() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/kensyu");
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}

	public DataSource getDataSource() {
		return ds;
	}
}
