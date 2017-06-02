package com.worksap.bootcamp.spring.bookstore.spec.dto;

public class CartAddResult {
	private final int newAmount;
	private final int overflowed;
	private final int shortage;
	private final int actualAdded;
	private final int maxCapacity;

	public CartAddResult(int newAmount, int overflowed, int shortage, int actualAdded, int maxCapacity) {
		this.shortage = shortage;
		this.newAmount = newAmount;
		this.overflowed = overflowed;
		this.actualAdded = actualAdded;
		this.maxCapacity = maxCapacity;
	}

	public int getShortage() {
		return shortage;
	}

	public int getNewAmount() {
		return newAmount;
	}

	public int getOverflowed() {
		return overflowed;
	}

	public int getActualAdded() {
		return actualAdded;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}
}
