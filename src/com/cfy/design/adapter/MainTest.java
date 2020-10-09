package com.cfy.design.adapter;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
*@author    created by ChenFangYa
*@date  2020-9-29---15:41:39
*@action
*/
public class MainTest {

	@Test
	public void testAdapter() {
		Map<String,String> map=new HashMap<>();
        map.put("029","121345265");
 
        //通过适配器创建新系统实例
        FacultyMember f=new MemberUser(new UserInfo("14","1","青州",map,"xiaobai"));
        System.out.println(f.getContactTelephone());
        System.out.println(f.getIdentification());
        System.out.println(f.toString());

	}
}
