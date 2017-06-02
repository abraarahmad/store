package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class Customer {
	private final String name;
	private final String address;

	public Customer(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}
}
