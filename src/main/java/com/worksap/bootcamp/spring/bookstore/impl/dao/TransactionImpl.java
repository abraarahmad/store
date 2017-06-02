package com.worksap.bootcamp.spring.bookstore.impl.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;

public class TransactionImpl implements Transaction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DataSourceHolder dataSourceHolder = new DataSourceHolder();

	private Connection connection;

	@Override
	public void begin() throws TransactionException {
		if (connection == null) {
			try {
				connection = dataSourceHolder.getDataSource().getConnection();
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	@Override
	public void commit() throws TransactionException {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new TransactionException(e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(),e);
				}

				connection = null;
			}
		}
	}

	@Override
	public void rollback() throws TransactionException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new TransactionException(e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(),e);
				}

				connection = null;
			}
		}
	}

	@Override
	public <T> T getResource(Class<T> klass) {
		if (klass.equals(Connection.class)) {
			@SuppressWarnings("unchecked")
			T con = (T)connection;

			return con;
		}

		throw new IllegalArgumentException();
	}

	@Override
	public boolean isActive() throws TransactionException {
		try {
			if (connection != null && connection.isClosed()) {
				connection = null;
				return false;
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}

		return true;
	}
}
