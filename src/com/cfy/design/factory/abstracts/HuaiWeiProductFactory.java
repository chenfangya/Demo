package com.cfy.design.factory.abstracts;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---13:47:54
*@action
*/
public class HuaiWeiProductFactory extends AbstractFactory {

	@Override
	public Phone createPhone() {
		return new HuaiWeiPhone();
	}

	@Override
	public Notebook createNotebook() {
		return new HuaiWeiNotebook();
	}

}
