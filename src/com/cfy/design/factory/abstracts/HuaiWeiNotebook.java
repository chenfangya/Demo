package com.cfy.design.factory.abstracts;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---13:42:53
*@action
*/
public class HuaiWeiNotebook implements Notebook {

	@Override
	public void printColor() {
		System.out.println("华为笔记本: 颜色 白色");
	}

	@Override
	public void printSize() {
		System.out.println("华为笔记本: 尺寸 15.6");
	}

}
