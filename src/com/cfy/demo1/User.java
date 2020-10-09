package com.cfy.demo1;

public class User {
	// 柜子
	private Cabinet cabinet;
	// 存储的数字
	private int storeNumber;

	public User(Cabinet cabinet, int storeNumber) {
		this.cabinet = cabinet;
		this.storeNumber = storeNumber;
	}

	// 使用柜子
	public synchronized void useCabinet() {
		cabinet.setStoreNumber(storeNumber);
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	public int getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}

	
}