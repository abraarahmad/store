package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Order;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderDetail;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;
import com.worksap.bootcamp.spring.bookstore.spec.services.ManageOrderService;

@Component
public class ManageOrderServiceImpl implements ManageOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Transaction transaction;
	private final OrderHeaderDao orderHeaderDao;
	private final OrderDetailDao orderDetailDao;

	@Autowired
	public ManageOrderServiceImpl(Transaction transaction, DaoFactory daoFactory) {
		this.transaction = daoFactory.getTransaction();
		this.orderHeaderDao = daoFactory.getOrderHeaderDao();
		this.orderDetailDao = daoFactory.getOrderDetailDao();
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
