package com.cfy.design.adapter;

import java.util.Map;

public class MemberUser extends FacultyMember {
	
	UserInfo iu; // 将被适配者作为适配器的成员变量

	public MemberUser() {
		super();
	}

	public MemberUser(UserInfo iUserInfo) {
		this.iu = iUserInfo;
	}

	// 重写getName方法，调用旧系统的getName，使得两个类name属性匹配。下面其他重写相同
	public String getName() {

		return iu.getName(); // 在适配器中重写目标的方法，使得通过目标类的方法调用就可以使
								// 用被适配者类的方法
	}

	public String getIdentification() {

		int id = Integer.parseInt(iu.getId());
		return iu.getNumber() + String.format("%04d", id);
	}

	public String getContactTelephone() {
		Map<String, String> map = iu.getTeles();
		String str = null;
		for (String key : map.keySet()) {
			String value = map.get(key);
			str = key + "-" + value;
		}
		return str;
	}

	@Override
	public String toString() {
		return "memberUser--" + "name:" + iu.getName() + " id:" + iu.getNumber() + iu.getId() + " homeAddress:"
				+ iu.getHomeAddress() + " teles:" + iu.getTeles();
	}
}