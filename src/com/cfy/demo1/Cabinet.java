package com.cfy.demo1;

import java.time.LocalDateTime;
import java.util.Date;

public class Cabinet {
	// 柜子中存储的数字
	private int storeNumber;

	public synchronized void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}

	public int getStoreNumber() {
		return this.storeNumber;
	}

	public static void main(String[] args) {
		System.out.println(LocalDateTime.now());
	}
}