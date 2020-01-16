package com.cfy.java8optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.junit.Test;

import com.cfy.entity.Person;
import com.cfy.entity.Student;
import com.cfy.exception.DemoException;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---上午10:54:46
*@action
*/
public class Demo {
	
	private String noValueFound = "No value found";
	private String havaValueFound = "Hava value found";

	@Test
	public void test() {
		Optional<Person> optPerson = Optional.empty(); //声明一个空的Optional
		
		Optional<Person> optPerson1 = Optional.of(getPerson(false)); //依据一个非空值创建一个Optional对象(如果car是一个null，这段代码会立即抛出一个NullPointerException)
		
		Optional<Person> optPerson2 = Optional.ofNullable(getPerson(true)); //创建一个允许null值的Optional对象(如果car是null，那么得到的Optional对象就是个空对象)


		System.out.println(optPerson);
		System.out.println(optPerson1);
		System.out.println(optPerson2);
	}
	
	
	/**
	 * String firstName = null; 
	 * if(person != null) { 
	 * 		firstName = person.getFirstName(); 
	 * }
	 */
	@Test
	public void test1() {
		Optional<Person> optPerson = Optional.ofNullable(getPerson(true));
		Optional<String> firstName = optPerson.map(Person::getFirstName);

		System.out.println(optPerson);
		System.out.println(firstName);
	}
	
	
	
	@Test
	public void test2() throws DemoException {
		Optional<Person> optPerson = Optional.ofNullable(getPerson(false));
		Optional<String> firstName = optPerson.map(Person::getFirstName);
		
		Optional<String> upperFirstName = firstName.flatMap(v -> Optional.of(v.toUpperCase()));

		System.out.println(upperFirstName);
		System.out.println(upperFirstName.get());//如果变量存在，它直接返回封装的变量值，否则就抛出一个NoSuchElementException异常
		System.out.println(upperFirstName.orElse(get(noValueFound)));// 它允许你在Optional对象不包含值时提供一个默认值。
		System.out.println(upperFirstName.orElseGet(() -> get(noValueFound)));//orElse方法的延迟调用版，Supplier方法只有在Optional对象不含值时才执行调用
		System.out.println(upperFirstName.orElseThrow(DemoException::new));//遭遇Optional对象为空时都会抛出一个异常

		upperFirstName.ifPresent(v -> get(havaValueFound));	//在变量值存在时执行一个作为参数传入的方法，否则就不进行任何操作
	}
	
	
	/**
	 * 两个 Optional 对象的组合
	 */
	@Test
	public void test3() {
		
		List<Person> list = new ArrayList<>();
		list.add(getPerson(false));
		
		if (!list.isEmpty()) {
			list.stream().filter(p -> "Chen".equals(p.getFirstName())).forEach(System.out::println);
		}
		
		
		//-------------------------------------Optional写法
		List<Person> listOpt = new ArrayList<>();
		listOpt.add(getPerson(false));
		Optional.ofNullable(listOpt).filter(
				listOptV -> listOptV.stream().anyMatch(p -> "Chen".equals(p.getFirstName()))
				).ifPresent(v -> v.forEach(System.out::println));
	}
	
	
	@Test
	public void test4() {
		Properties props = new Properties();
		props.setProperty("a", "5");
		props.setProperty("b", "true");
		props.setProperty("c", "-3");
		
		int i = readDuration(props, "a");
		System.out.println(i);
	}
	
	@Test
	public void test5() {
		Optional<Person> nullSafeFindStudent = nullSafeFindStudent(Optional.ofNullable(getPerson(false)), Optional.ofNullable(getStudent(false)));
		System.out.println(nullSafeFindStudent);
	}
	
	
	
	
	public int readDuration(Properties props, String name) {
		return Optional.ofNullable(props.getProperty(name))
					   .flatMap(OptionalUtility::stringToInt)
					   .filter(i -> i > 0)
					   .orElse(0);
	}
	
	static class OptionalUtility {
		
		private OptionalUtility() {
			super();
		}

		public static Optional<Integer> stringToInt(String s) {
			try {
				return Optional.of(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}
	}

	
	
	public Optional<Person> nullSafeFindStudent(
			Optional<Person> person, Optional<Student> student) {
			return person.flatMap(p -> student.map(s -> findSomebodyIsStudent(p, s)));
		}

	
	public Person findSomebodyIsStudent(Person person, Student student) {
		Person p = new Person();
		p.setFirstName("findSomebodyIsStudent");
		return p;
	}

	
	
	
	 public String get(String name) {
        System.out.println(name + "执行了get方法");
        return name;
    }
	
	
	
	public Person getPerson(boolean emptyValue) {
		Person person = new Person();
		if (!emptyValue) {
			person.setFirstName("Chen");
			person.setLastName("FangYa");
		}
		 return person;
	 }
	 
	 
	 
	public Student getStudent(boolean emptyValue) {
		Student student = new Student();
		if (!emptyValue) {
			student.setAge(10);
			student.setName("ChenFangYa");
		}
		return student;
	}
	
	
	
	
	
}
