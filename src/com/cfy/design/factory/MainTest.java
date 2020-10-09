package com.cfy.design.factory;

import org.junit.jupiter.api.Test;

import com.cfy.design.factory.abstracts.HuaiWeiProductFactory;
import com.cfy.design.factory.abstracts.Notebook;
import com.cfy.design.factory.abstracts.Phone;
import com.cfy.design.factory.abstracts.XiaoMiProductFactory;
import com.cfy.design.factory.method.AbstractFactory;
import com.cfy.design.factory.method.ConcreateFactoryA;
import com.cfy.design.factory.method.ConcreateFactoryB;
import com.cfy.design.factory.method.IProduct;
import com.cfy.design.factory.simple.Computer;
import com.cfy.design.factory.simple.SimpleFactory;

/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:51:29
*@action
*	工厂模式的定义：
*		定义一个创建产品对象的工厂接口，将产品对象的实际创建工作推迟到具体子工厂类当中。这满足创建型模式中所要求的“创建与使用相分离”的特点。
*	
*	简单工厂模式
*		优点：
*			工厂类包含必要的逻辑判断，可以决定在什么时候创建哪一个产品的实例。客户端可以免除直接创建产品对象的职责，很方便的创建出相应的产品。工厂和产品的职责区分明确。
*			客户端无需知道所创建具体产品的类名，只需知道参数即可。
*			也可以引入配置文件，在不修改客户端代码的情况下更换和添加新的具体产品类。
*		缺点：
*			简单工厂模式的工厂类单一，负责所有产品的创建，职责过重，一旦异常，整个系统将受影响。且工厂类代码会非常臃肿，违背高聚合原则。
*			使用简单工厂模式会增加系统中类的个数（引入新的工厂类），增加系统的复杂度和理解难度
*			统扩展困难，一旦增加新产品不得不修改工厂逻辑，在产品类型较多时，可能造成逻辑过于复杂
*			简单工厂模式使用了 static 工厂方法，造成工厂角色无法形成基于继承的等级结构。
*
*		简单工厂模式的主要角色如下：
*			简单工厂（SimpleFactory）：是简单工厂模式的核心，负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。
*			抽象产品（Product）：是简单工厂创建的所有对象的父类，负责描述所有实例共有的公共接口。
*			具体产品（ConcreteProduct）：是简单工厂模式的创建目标。
*
*	抽象工厂模式
*
*	
*/
public class MainTest {

	@Test
	public void testSimpleFactory() {
		Computer computer = SimpleFactory.createComputer("laptop");
		computer.chat();
		computer.work();
		computer.watchMovie();
	}
	
	@Test
	public void testAbstractFactory() {
		com.cfy.design.factory.abstracts.AbstractFactory huaiWeiProductFactory = new HuaiWeiProductFactory();
		com.cfy.design.factory.abstracts.AbstractFactory xiaoMiProductFactory = new XiaoMiProductFactory();
		
		Notebook notebook = huaiWeiProductFactory.createNotebook();
		Phone phone = huaiWeiProductFactory.createPhone();
		notebook.printColor();
		notebook.printSize();
		phone.printConfig();
		
		Notebook notebook1 = xiaoMiProductFactory.createNotebook();
		Phone phone1 = xiaoMiProductFactory.createPhone();
		notebook1.printColor();
		notebook1.printSize();
		phone1.printConfig();
	}
	
	@Test
	public void testMethodFactory() {
		AbstractFactory factoryA = new ConcreateFactoryA();
		AbstractFactory factoryB = new ConcreateFactoryB();
		IProduct productA = factoryA.createProduct();
		IProduct productB = factoryB.createProduct();
		productA.method();
		productB.method();
	}
}
