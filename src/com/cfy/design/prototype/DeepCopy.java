package com.cfy.design.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author created by ChenFangYa
 * @date 2020-9-21---10:29:36
 * @action 深拷贝不仅会复制成员变量为基本数据类型的值，给新对象。 还会给是引用数据类型的成员变量申请储存空间，并复制引用数据类型成员变量的对象。
 *         这样拷贝出的新对象就不怕修改了是引用数据类型的成员变量后，对其它拷贝出的对象造成影响了。
 */
public class DeepCopy implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8637076617102483010L;

	private String name;
	private int age;

	private Person person;
	
	public DeepCopy(String name, int age, Person person) {
		super();
		this.name = name;
		this.age = age;
		this.person = person;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public String toString() {
		return "DeepCopy [name=" + name + ", age=" + age + ", person=" + person + "]";
	}

	/**需要单独处理所有引用类型*/
	@Override
	protected Object clone() throws CloneNotSupportedException {
		DeepCopy deepcopy = null;
		try {
			deepcopy = (DeepCopy) super.clone();
			deepcopy.person = (Person) this.person.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return super.clone();
	}
	

	/**通过序列化对象实现深拷贝*/
	public DeepCopy deepCopy() {
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			return (DeepCopy) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bos.close();
				bis.close();
				oos.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
