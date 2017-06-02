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

import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Item;

public class ItemDaoImpl implements ItemDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Item find(Transaction transaction, int id) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("select item_id, item_name, price, picture, release_date from items where item_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDate(5));
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
	public List<Item> getAllOrderdById(Transaction transaction) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("select item_id, item_name, price, picture, release_date from items order by item_id");
			rs = ps.executeQuery();

			List<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				items.add(new Item(rs.getInt(1), rs.getString(2), rs.getInt(3),
						rs.getString(4), rs.getDate(5)));
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