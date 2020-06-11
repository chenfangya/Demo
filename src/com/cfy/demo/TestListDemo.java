package com.cfy.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
*@author    created by ChenFangYa
*@date  2020年6月9日---上午10:58:27
*@action
*/
public class TestListDemo {

	public static void main(String[] args) {
//		List<Integer> list = new ArrayList<>(1000000);
		List<Integer> list = new LinkedList<>();
		long start = System.currentTimeMillis();
		Random random = new Random();
		for (Integer i = 0; i < 1000000; i++) {
			list.add(random.nextInt(1000000));
		}
		long end1 = System.currentTimeMillis();
		System.out.println("耗时 ： " + ( end1- start));
		
		for (int i = 0; i < 10; i++) {
			System.out.println(list.get(random.nextInt(1000000)));
		}
		System.out.println("耗时1 ： " + (System.currentTimeMillis()- end1));
	}
	
	@Test
	public void test1() {
		List<Integer> l1 = new ArrayList<>();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(7);
		List<Integer> l2 = new ArrayList<>();
		l2.add(1);
		l2.add(2);
		l2.add(3);
		l2.retainAll(l1);
		System.out.println(l2);
		System.out.println(l1);
		l1.removeAll(l2);
		System.out.println(l2);
		System.out.println(l1);
	}
}
