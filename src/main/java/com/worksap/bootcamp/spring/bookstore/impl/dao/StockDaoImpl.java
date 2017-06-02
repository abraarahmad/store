package com.worksap.bootcamp.spring.bookstore.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.Transaction;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;

public class StockDaoImpl implements StockDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Stock> getAllOrderedByItemId(Transaction transaction) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS order by item_id");
			rs = ps.executeQuery();

			List<Stock> items = new ArrayList<Stock>();

			while (rs.next()) {
				items.add(new Stock(rs.getInt(1), rs.getInt(2)));
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
	public List<Stock> findByItemIdWithLock(Transaction transaction, Set<Integer> idList) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			StringBuilder sb = new StringBuilder();

			for (int i = 0 ; i < idList.size(); i++) {
				if (sb.length() == 0) {
					sb.append("?");
					continue;
				}

				sb.append(",?");
			}

			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS where item_id in ("+ sb.toString()+") FOR UPDATE");

			int index = 1;

			for (Integer id : idList) {
				ps.setInt(index, id);
				index++;
			}

			rs = ps.executeQuery();

			List<Stock> result = new ArrayList<Stock>();

			while (rs.next()) {
				result.add(new Stock(rs.getInt(1), rs.getInt(2)));
			}

			return result;
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
	public void updateStock(Transaction transaction, int itemId, int newStock) throws IOException {
		PreparedStatement ps = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("update stocks set stock = ?, prc_date = now() where item_id = ?");
			ps.setInt(1, newStock);
			ps.setInt(2, itemId);
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
	public Stock find(Transaction transaction, int itemId) throws IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Connection con = transaction.getResource(Connection.class);
			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS where item_id = ?");
			ps.setInt(1, itemId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new Stock(rs.getInt(1), rs.getInt(2));
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
