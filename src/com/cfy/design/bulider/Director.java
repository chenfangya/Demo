package com.cfy.design.bulider;

import java.util.ArrayList;
import java.util.List;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:29:39
*@action
*/
public class Director {

	private List<String> sequence = new ArrayList<>();
	
	private CarBuilder car1Builder = new Car1Builder();
	private CarBuilder car2Builder = new Car2Builder();
	
	public Car1 getCar1() {
		this.sequence.clear();
		this.sequence.add("start");
		this.sequence.add("engineBoom");
		this.sequence.add("alarm");
		this.sequence.add("stop");
		this.car1Builder.setSequence(this.sequence);
		return (Car1) this.car1Builder.getCar();
	}
	
	public Car2 getCar2() {
		this.sequence.clear();
		this.sequence.add("alarm");
		this.sequence.add("engineBoom");
		this.sequence.add("start");
		this.sequence.add("stop");
		this.car2Builder.setSequence(this.sequence);
		return (Car2) this.car2Builder.getCar();
	}
}
