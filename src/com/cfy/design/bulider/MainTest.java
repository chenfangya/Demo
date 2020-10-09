package com.cfy.design.bulider;

import org.junit.jupiter.api.Test;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---14:13:51
*@action
*
*	建造者模式具有以下优点：
*		1. 封装性好，客户端不必知道内部产品的实现细节。
*		2. 建造者独立，容易扩展。
*		3. 便于控制细节风险。
*	知道了它的优点，再来说说什么时候用建造者模式：
*		1. 相同的方法，不同的执行顺序，产生不同的事件结果时，可以采用建造者模式。
*		2. 多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时，则可以使用该模式。
*		3. 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的效能，这个时候使用建造者模式非常合适。
*		4. 在对象创建过程中会使用到系统中的一些其他对象，这些对象在产品对象的创建过程中不易得到时，也可以采用建造者模式封装该对象的创建过程。
*/
public class MainTest {
	
	@Test
	public void testBuilder() {
		Director director = new Director();
		Car1 car1 = director.getCar1();
		car1.run();
		Car2 car2 = director.getCar2();
		car2.run();
	}
}
