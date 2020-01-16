package com.cfy.strategy;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---上午9:53:31
*@action
*/
public enum UserType {

	ORDINARY_VIP(0, "ordinary"),
	SILVER_VIP(1, "silver"), 
	GOLD_VIP(2, "gold"), 
	PLATINUM_VIP(3, "platinum"),;
	
	private int code;  
    private String type;
    


	private UserType(int code, String type) {
		this.code = code;
		this.type = type;
	}
	
	public int getCode() {
		return code;
	}

	public String getType() {
		return type;
	}
}
