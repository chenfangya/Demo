package com.cfy.mianshi;

import com.cfy.entity.InstanceDemo;
import com.cfy.entity.Person;
import com.cfy.entity.Student;

/**
*@author    created by ChenFangYa
*@date  2019年12月24日---上午10:57:17
*@action
*/
public class Demo2 {

	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					Student student = InstanceDemo.INSTANCE.getStudent();
					Student student2 = InstanceDemo.INSTANCE.getStudent();
					
					
					
					Person person = InstanceDemo.INSTANCE2.getPerson();
					Person person2 = InstanceDemo.INSTANCE2.getPerson();
					
//					if ((student != student2)  || (person != person2) ) {
						System.out.println("线程： " + Thread.currentThread().getName() + " ,  student = " + student);
						System.out.println("线程： " + Thread.currentThread().getName() + " ,  student2 = " + student2);
						System.out.println("线程： " + Thread.currentThread().getName() + "student==student2 :" + (student == student2));
						System.out.println("线程： " + Thread.currentThread().getName() + " ,  person = " + person);
						System.out.println("线程： " + Thread.currentThread().getName() + " ,  person2 = " + person2);
						System.out.println("线程： " + Thread.currentThread().getName() + "person==person2 :" + (person == person2));
//					}
				}
			}.start();
		}
	}
}
