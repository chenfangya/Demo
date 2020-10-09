package com.cfy.design.bulider;

import java.util.List;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:24:49
*@action
*/
public class Car1Builder extends CarBuilder {
	
	private Car1 car1 = new Car1();

	@Override
	protected void setSequence(List<String> sequence) {
		this.car1.setSequence(sequence);
	}

	@Override
	protected AbstractCar getCar() {
		return this.car1;
	}

}
