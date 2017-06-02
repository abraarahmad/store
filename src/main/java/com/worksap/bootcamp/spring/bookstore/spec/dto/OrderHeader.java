package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class OrderHeader {
	private final int id;
	private final int total;
	private final String name;
	private final String address;

	public OrderHeader(int id, int total, String name, String address) {
		this.id = id;
		this.total = total;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public int getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
}
