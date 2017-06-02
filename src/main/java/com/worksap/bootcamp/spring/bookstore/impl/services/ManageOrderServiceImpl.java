package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderDetail;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;

public class ManageOrderServiceImpl implements ManageOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Transaction transaction;
	private final OrderHeaderDao orderHeaderDao;
	private final OrderDetailDao orderDetailDao;

	public ManageOrderServiceImpl(Transaction transaction, OrderHeaderDao orderHeaderDao, OrderDetailDao orderDetailDao) {
		this.transaction = transaction;
		this.orderHeaderDao = orderHeaderDao;
		this.orderDetailDao = orderDetailDao;
	}

	@Override
	public List<OrderHeader> getOrderHeaders() {
		try {
			transaction.begin();

			List<OrderHeader> result = orderHeaderDao.findAll(transaction);

			transaction.commit();

			return result;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch (RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}

	@Override
	public Order getOrder(int orderHeaderId) {
		try {
			transaction.begin();

			OrderHeader header = orderHeaderDao.find(transaction, orderHeaderId);
			List<OrderDetail> details = orderDetailDao.findByHeaderId(transaction, orderHeaderId);

			transaction.commit();

			return new Order(header, details);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch (RuntimeException e) {
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}
}
