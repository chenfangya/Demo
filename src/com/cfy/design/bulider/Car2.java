package com.cfy.design.bulider;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:12:41
*@action
*/
public class Car2 extends AbstractCar {

	@Override
	protected void start() {
		System.out.println("Car2 start");
	}

	@Override
	protected void engineBoom() {
		System.out.println("Car2 engineBoom");
	}

	@Override
	protected void alarm() {
		System.out.println("Car2 alarm");
	}

	@Override
	protected void stop() {
		System.out.println("Car2 stop");
	}

}
