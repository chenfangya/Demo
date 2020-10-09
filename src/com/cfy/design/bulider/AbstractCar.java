package com.cfy.design.bulider;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:08:25
*@action
*/

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCar {
	
	private List<String> sequence = new ArrayList<>();

	public final void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}
	
	protected abstract void start();
	protected abstract void engineBoom();
	protected abstract void alarm();
	protected abstract void stop();
	
	public void run() {
		this.sequence.forEach(v -> {
			if ("start".equals(v)) {
				this.start();
			} else if ("engineBoom".equals(v)) {
				this.engineBoom();
			} else if ("alarm".equals(v)) {
				this.alarm();
			} else if ("stop".equals(v)) {
				this.stop();
			}
		});
	}
}
