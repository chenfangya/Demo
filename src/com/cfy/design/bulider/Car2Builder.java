package com.cfy.design.bulider;

import java.util.List;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:24:49
*@action
*/
public class Car2Builder extends CarBuilder {
	
	private Car2 car2 = new Car2();

	@Override
	protected void setSequence(List<String> sequence) {
		this.car2.setSequence(sequence);
	}

	@Override
	protected AbstractCar getCar() {
		return this.car2;
	}

}
