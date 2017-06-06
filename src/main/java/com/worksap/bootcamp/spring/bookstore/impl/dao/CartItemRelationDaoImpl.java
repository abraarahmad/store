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
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;

@Component
public class CartItemRelationDaoImpl implements CartItemRelationDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void create(Transaction transaction, CartItemRelation item) throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("insert into cart_items (user_id, item_id, amount, prc_date) values (?, ?, ?, now())");
			ps.setString(1, item.getUserId());
			ps.setInt(2, item.getItemId());
			ps.setInt(3, item.getAmount());
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
	public List<CartItemRelation> findByUserId(Transaction transaction, String userId) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("select user_id, item_id, amount from cart_items where user_id = ?");
			ps.setString(1, userId);
			rs = ps.executeQuery();

			List<CartItemRelation> cartItems = new ArrayList<CartItemRelation>();

			while(rs.next()) {
				cartItems.add(new CartItemRelation(rs.getString(1), rs.getInt(2), rs.getInt(3)));
			}

			return cartItems;
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
	public CartItemRelation findByUserIdAndItemId(Transaction transaction, String userId, int itemId) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("select user_id, item_id, amount from cart_items where item_id = ? and user_id = ?");
			ps.setInt(1, itemId);
			ps.setString(2, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new CartItemRelation(rs.getString(1), rs.getInt(2), rs.getInt(3));
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

	@Override
	public void updateAmount(Transaction transaction, String userId, int itemId, int newAmount) throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("update cart_items set amount = ?, prc_date = now() where user_id = ? and item_id = ?");
			ps.setInt(1, newAmount);
			ps.setString(2, userId);
			ps.setInt(3, itemId);
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
	public void remove(Transaction transaction, String userId, int itemId) throws IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("delete from cart_items where user_id = ? and item_id = ?");
			ps.setString(1, userId);
			ps.setInt(2, itemId);
			ps.executeUpdate();
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
	public void removeByUserId(Transaction transaction, String userId)
			throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("delete from cart_items where user_id = ?");
			ps.setString(1, userId);
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
}
