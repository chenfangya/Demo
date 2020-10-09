package com.cfy.design.prototype;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:32:26
*@action
*/
public interface Shape extends Cloneable {

	/** 拷贝 */
	Object clone();
	/**计算面积*/
    void countArea();
}
