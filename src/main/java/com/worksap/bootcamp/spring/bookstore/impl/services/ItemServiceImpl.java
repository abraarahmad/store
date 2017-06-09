package com.worksap.bootcamp.spring.bookstore.impl.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.bootcamp.spring.bookstore.spec.dao.DaoFactory;
import com.worksap.bootcamp.spring.bookstore.spec.dao.ItemDao;
import com.worksap.bootcamp.spring.bookstore.spec.dao.StockDao;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Item;
import com.worksap.bootcamp.spring.bookstore.spec.dto.ItemStock;
import com.worksap.bootcamp.spring.bookstore.spec.dto.Stock;
import com.worksap.bootcamp.spring.bookstore.spec.services.ItemService;

@Component
public class ItemServiceImpl implements ItemService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ItemDao itemDao;
	private final StockDao stockDao;
	//private final Transaction transaction;

	@Autowired
	public ItemServiceImpl(DaoFactory daoFactory) {
		this.itemDao = daoFactory.getItemDao();
		this.stockDao = daoFactory.getStockDao();
		//this.transaction = daoFactory.getTransaction();
	}

	@Transactional
	@Override
	public List<ItemStock> getOnSale() {
		return filterItemStock(findItemStock());
	}

	private List<ItemStock> filterItemStock(List<ItemStock> target) {
		Iterator<ItemStock> it = target.iterator();

		for (;it.hasNext();) {
			ItemStock itemStock = it.next();
			if (itemStock.getItem().getReleaseDate().after(new Date())) {
				it.remove();
				continue;
			}
		}

		return target;
	}

	
	private List<ItemStock> findItemStock() {
		try {
			//transaction.begin();

			Iterable<Item> items = itemDao.getAllOrderdById();
			Iterator<Stock> stocks = stockDao.getAllOrderedByItemId().iterator();
			List<ItemStock> itemStocks = new ArrayList<ItemStock>();
			Stock currentStock = stocks.hasNext() ? stocks.next() : null;

			for (Item item : items) {
				if (currentStock == null) {
					break;
				}

				while(currentStock != null) {
					if (currentStock.getItemId() > item.getId()) {
						break;
					}

					if (currentStock.getItemId() < item.getId()) {
						currentStock = stocks.hasNext() ? stocks.next() : null;
						continue;
					}

					if (currentStock.getItemId() == item.getId()) {
						itemStocks.add(new ItemStock(item, currentStock));
						currentStock = stocks.hasNext() ? stocks.next() : null;
					}
				}
			}

			//transaction.commit();

			return itemStocks;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
