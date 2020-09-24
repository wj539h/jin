package com.jin.java8.p3;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.jin.java8.Employee;

/**
 * 二、Lambda表达式需要“函数式接口”的支持 只包含一个抽象方法的接口，称为 函数式接口。
 * 
 * 你可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda表达式抛出一个受检异常，那么该异常需要在目标接口的抽象方法上进行声明）。
 * 
 * 我们可以在任意函数式接口上使用 @FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口，
 * 同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。
 * 
 * 作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递，接收 Lambda 表达式的参数类型必须是与该 Lambda 
 * 表达式兼容的函数式接口的类型。
 * 
 * @author Administrator
 *
 */
public class PracticeLambda {
    
//    @Test
    public void test6() {
	Integer i = operation(1000, new MyFun() {

	    @Override
	    public Integer getValue(Integer num) {
		return num+=100;
	    }
	    
	});
	
	assertEquals(Integer.valueOf(1100), i);
	
	Integer j = operation(500, a -> ++a);//or  a -> {return a++;}
	
	assertEquals(Integer.valueOf(501), j);
    }
    
    public Integer operation(Integer a, MyFun mf) {
	return mf.getValue(a);
    }
    

    /**
     * 练习：调用Collections.sort()方法，通过定制排序比较两个Employee(先按年龄比，年龄相同按姓名比)，使用Lambda作为参数传递。
     */
    @Test
    public void test7() {
	List<Employee> eList = Employee.init();
	System.out.println(eList);
	Collections.sort(eList, new Comparator<Employee>() {
	    @Override
	    public int compare(Employee e1, Employee e2) {
		if(e1.getAge().compareTo(e2.getAge()) == 0) {
		    return e1.getName().compareTo(e2.getName());
		}else {
		    return Integer.compare(e1.getAge(), e2.getAge());
		}
	    }
	});
	System.out.println(eList);
	assertEquals(Double.valueOf("86.2"), eList.get(0).getSalary());
	
	eList = Employee.init();
	Collections.sort(eList, (e1,e2)->{return e1.getAge().compareTo(e2.getAge()) == 0 ?
		e1.getName().compareTo(e2.getName()) : Integer.compare(e1.getAge(), e2.getAge());}
	);
    }
}
