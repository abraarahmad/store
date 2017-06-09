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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;

@Component
public class StockDaoImpl implements StockDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private JdbcTemplate template;

	  @Autowired
	  public StockDaoImpl(JdbcTemplate template) {
	    this.template = template;
	  }
	  
	  
	@Override
	public List<Stock> getAllOrderedByItemId() throws IOException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS order by item_id");
//			rs = ps.executeQuery();
//
//			List<Stock> items = new ArrayList<Stock>();
//
//			while (rs.next()) {
//				items.add(new Stock(rs.getInt(1), rs.getInt(2)));
//			}
//
//			return items;
//		} catch (SQLException e) {
//			throw new IOException(e);
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//		}
		return template.query("SELECT item_id, stock FROM STOCKS order by item_id",
			      ps -> {},
			      (rs, rowNum) -> new Stock(rs.getInt(1), rs.getInt(2))
			      );
	}

	@Override
	public List<Stock> findByItemIdWithLock(Set<Integer> idList) throws IOException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
			StringBuilder sb = new StringBuilder();

			for (int i = 0 ; i < idList.size(); i++) {
				if (sb.length() == 0) {
					sb.append("?");
					continue;
				}

				sb.append(",?");
			}

//			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS where item_id in ("+ sb.toString()+") FOR UPDATE");
//
//			int index = 1;
//
//			for (Integer id : idList) {
//				ps.setInt(index, id);
//				index++;
//			}
//
//			rs = ps.executeQuery();
//
//			List<Stock> result = new ArrayList<Stock>();
//
//			while (rs.next()) {
//				result.add(new Stock(rs.getInt(1), rs.getInt(2)));
//			}
//
//			return result;
//		} catch (SQLException e) {
//			throw new IOException(e);
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
			
//		}
			
			return template.query("SELECT item_id, stock FROM STOCKS where item_id in ("+ sb.toString()+") FOR UPDATE",
				      ps -> {
					    	  int index = 1;
					    	  for (Integer id : idList) {
								ps.setInt(index, id);
								index++;
					    	  }
					    	 },
				      (rs, rowNum) ->new Stock(rs.getInt(1), rs.getInt(2))
				      );	
	}

	@Override
	public void updateStock(int itemId, int newStock) throws IOException {
//		PreparedStatement ps = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("update stocks set stock = ?, prc_date = now() where item_id = ?");
//			ps.setInt(1, newStock);
//			ps.setInt(2, itemId);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			throw new IOException(e);
//		} finally {
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//		}
		template.update(
			      "update stocks set stock = ?, prc_date = now() where item_id = ?",
			      ps -> {
			    	  ps.setInt(1, newStock);
					  ps.setInt(2, itemId);
			      });
	}

	@Override
	public Stock find(int itemId) throws IOException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("SELECT item_id, stock FROM STOCKS where item_id = ?");
//			ps.setInt(1, itemId);
//			rs = ps.executeQuery();
//
//			if (rs.next()) {
//				return new Stock(rs.getInt(1), rs.getInt(2));
//			}
//
//			return null;
//		} catch (SQLException e) {
//			throw new IOException(e);
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//		}
		
		return DataAccessUtils.requiredSingleResult(
			      template.query("SELECT item_id, stock FROM STOCKS where item_id = ?",
			          ps -> ps.setInt(1, itemId),
			          (rs, rowNum) -> new Stock(rs.getInt(1), rs.getInt(2))
			          ));
	}
}
