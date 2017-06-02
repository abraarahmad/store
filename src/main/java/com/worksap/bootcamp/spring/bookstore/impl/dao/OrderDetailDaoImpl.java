package com.worksap.bootcamp.spring.bookstore.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderDetailDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderDetail;

public class OrderDetailDaoImpl implements OrderDetailDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void create(Transaction transaction,List<OrderDetail> orderDetails) throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("insert into order_details (order_header_id, item_id, amount, prc_date) values (?, ?, ?, now())");

			for (OrderDetail orderDetail : orderDetails) {
				ps.setInt(1, orderDetail.getOrderHeaderId());
				ps.setInt(2, orderDetail.getItemId());
				ps.setInt(3, orderDetail.getAmount());
				ps.addBatch();
			}

			ps.executeBatch();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public List<OrderDetail> findByHeaderId(Transaction transaction, int orderHeaderId) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("select order_header_id, item_id, amount from order_details where order_header_id = ?");
			ps.setInt(1, orderHeaderId);
			rs = ps.executeQuery();

			List<OrderDetail> items = new ArrayList<OrderDetail>();

			while (rs.next()) {
				items.add(new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}

			return items;
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}
}
