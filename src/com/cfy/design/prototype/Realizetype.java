package com.cfy.design.prototype;

//具体原型类
public class Realizetype implements Cloneable {
	Realizetype() {
		System.out.println("具体原型创建成功！");
	}

	public Object clone() throws CloneNotSupportedException {
		System.out.println("具体原型复制成功！");
		return (Realizetype) super.clone();
	}
}