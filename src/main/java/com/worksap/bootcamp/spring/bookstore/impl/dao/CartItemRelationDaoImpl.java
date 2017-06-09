package com.worksap.bootcamp.spring.bookstore.impl.dao;

import static org.hamcrest.CoreMatchers.nullValue;

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
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.worksap.bootcamp.spring.bookstore.spec.dao.CartItemRelationDao;
import com.worksap.bootcamp.spring.bookstore.spec.dto.CartItemRelation;

@Component
public class CartItemRelationDaoImpl implements CartItemRelationDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JdbcTemplate template;

	  @Autowired
	  public CartItemRelationDaoImpl(JdbcTemplate template) {
	    this.template = template;
	  }
	  
	@Override
	public void create(final CartItemRelation item) throws IOException {
		  template.update(
		      "insert into cart_items (user_id, item_id, amount, prc_date) values (?, ?, ?, now())",
		      ps -> {
		        ps.setString(1, item.getUserId());
		        ps.setInt(2, item.getItemId());
		        ps.setInt(3, item.getAmount());
		      });
		}

	@Override
	public List<CartItemRelation> findByUserId(String userId) throws IOException {
		  return template.query("select user_id, item_id, amount from cart_items where user_id = ?",
		      ps -> ps.setString(1, userId),
		      (rs, rowNum) -> new CartItemRelation(rs.getString(1), rs.getInt(2), rs.getInt(3))
		      );
		}

	@Override
	public CartItemRelation findByUserIdAndItemId(String userId, int itemId) throws IOException {
		  return DataAccessUtils.requiredSingleResult(
		      template.query("select user_id, item_id, amount from cart_items where item_id = ? and user_id = ?",
		          ps -> {
		              ps.setInt(1, itemId);
		              ps.setString(2, userId);
		          },
		          (rs, rowNum) -> { 
		        	  				  if(rs==null){
		        	  					  return null;
		        	  				  }
		                        	  return new CartItemRelation(rs.getString(1), rs.getInt(2), rs.getInt(3));
		        	                }
		                   
		          ));
		}

	@Override
	public void updateAmount(String userId, int itemId, int newAmount) throws IOException {
//		PreparedStatement ps = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("update cart_items set amount = ?, prc_date = now() where user_id = ? and item_id = ?");
//			ps.setInt(1, newAmount);
//			ps.setString(2, userId);
//			ps.setInt(3, itemId);
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
			      "update cart_items set amount = ?, prc_date = now() where user_id = ? and item_id = ?",
			      ps -> {
			    	  ps.setInt(1, newAmount);
					  ps.setString(2, userId);
				      ps.setInt(3, itemId);
			      });
		
	}

	@Override
	public void remove(String userId, int itemId) throws IOException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("delete from cart_items where user_id = ? and item_id = ?");
//			ps.setString(1, userId);
//			ps.setInt(2, itemId);
//			ps.executeUpdate();
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
		
		 template.update(
			      "delete from cart_items where user_id = ? and item_id = ?",
			      ps -> {
			    	  ps.setString(1, userId);
					  ps.setInt(2, itemId);
			      });
	}

	@Override
	public void removeByUserId(String userId)
			throws IOException {
//		PreparedStatement ps = null;
//
//		try {
//			Connection con = template.getDataSource().getConnection();
//			ps = con.prepareStatement("delete from cart_items where user_id = ?");
//			ps.setString(1, userId);
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
			      "delete from cart_items where user_id = ?",
			      ps -> ps.setString(1, userId));
	}
}
