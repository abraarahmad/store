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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.OrderHeaderDao;

import com.worksap.bootcamp.spring.bookstore.spec.dto.OrderHeader;

@Component
public class OrderHeaderDaoImpl implements OrderHeaderDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JdbcTemplate template;

	  @Autowired
	  public OrderHeaderDaoImpl(JdbcTemplate template) {
	    this.template = template;
	  }
	  
	@Override
	public void create(OrderHeader orderHeader) throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = template.getDataSource().getConnection();
			ps = con.prepareStatement("insert into order_headers (order_header_id, total_amount, customer_name, customer_address, order_date, prc_date) values (?, ?, ?, ?, now(), now())");
			ps.setInt(1, orderHeader.getId());
			ps.setInt(2, orderHeader.getTotal());
			ps.setString(3, orderHeader.getName());
			ps.setString(4, orderHeader.getAddress());
			ps.executeUpdate();
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
	public int getSequence() throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = template.getDataSource().getConnection();
			ps = con.prepareStatement("select order_header_id_seq.nextval");
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

			throw new IllegalStateException();
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

	@Override
	public List<OrderHeader> findAll() throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = template.getDataSource().getConnection();
			ps = con.prepareStatement("select order_header_id, total_amount, customer_name, customer_address, order_date from order_headers order by order_header_id");
			rs = ps.executeQuery();

			List<OrderHeader> items = new ArrayList<OrderHeader>();

			while (rs.next()) {
				items.add(new OrderHeader(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
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

	@Override
	public OrderHeader find(int orderHeaderId) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = template.getDataSource().getConnection();
			ps = con.prepareStatement("select order_header_id, total_amount, customer_name, customer_address, order_date from order_headers where order_header_id = ?");
			ps.setInt(1, orderHeaderId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new OrderHeader(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
			}

			return null;
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
