package com.cfy.design.bulider;

import java.util.List;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:22:38
*@action
*/
public abstract class CarBuilder {

	protected abstract void setSequence(List<String> sequence);
	
	protected abstract AbstractCar getCar();
}
