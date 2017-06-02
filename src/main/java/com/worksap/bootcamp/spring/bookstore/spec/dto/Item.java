package com.worksap.bootcamp.spring.bookstore.spec.dto;

import java.util.Date;

public class Item {
	private final int id;
	private final String name;
	private final int price;
	private final String picture;
	private final Date releaseDate;

	public Item() {
		this.id = -1;
		this.name = null;
		this.price = -1;
		this.picture = null;
		this.releaseDate = null;
	}

	public Item(int id, String name, int price, String picture, Date releaseDate) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.picture = picture;
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getPicture() {
		return picture;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
}
