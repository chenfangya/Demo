package com.cfy.design.factory.simple;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:52:34
*@action
*/
public class SimpleFactory {

	public static Computer createComputer(String type) {
		Computer computer = null;
		switch (type) {
		case "laptop":
			computer = new Laptop();
			break;
		case "smartPhone":
			computer = new SmartPhone();
			break;
		case "tablet":
			computer = new Tablet();
			break;
		default:
			throw new IllegalArgumentException("参数错误");
		}
		return computer;
	}
}
