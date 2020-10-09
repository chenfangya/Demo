package com.cfy.design.factory.abstracts;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---13:48:41
*@action
*/
public class XiaoMiProductFactory extends AbstractFactory {

	@Override
	public Phone createPhone() {
		return new XiaoMiPhone();
	}

	@Override
	public Notebook createNotebook() {
		return new XiaoMiNoteBook();
	}

}
