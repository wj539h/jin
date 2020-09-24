package com.jin.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Employee {
    
    public static List<Employee> init() {
	List<Employee> employees=Arrays.asList(
	            new Employee("张三",18,9496.2),
	            new Employee("李四",52,2396.2),
	            new Employee("王五",56,996.2),
	            new Employee("赵六",8,94.2),
	            new Employee("田七",8,86.2)
	    );
	return employees;
    }
    public static List<Employee> init1() {
	List<Employee> employees=Arrays.asList(
	            new Employee("张三",18,9999.99),
	            new Employee("李四",58,5555.55),
	            new Employee("王五",26,3333.33),
	            new Employee("赵六",36,6666.66),
	            new Employee("田七",12,8888.88),
	            new Employee("田七",12,8888.88)
	            );
	return employees;
    }
    
    private Integer id;
    private String name;
    private Integer age;
    private Double salary;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Employee(String name, Integer age, Double salary) {
	super();
	this.name = name;
	this.age = age;
	this.salary = salary;
    }
    public Employee(Integer id, String name, Integer age, Double salary) {
	super();
	this.id = id;
	this.name = name;
	this.age = age;
	this.salary = salary;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public Employee(String name,Integer id) {
	super();
	this.id = id;
	this.name = name;
    }
    public Employee(Integer age) {
	super();
	this.age = age;
    }
    public Employee() {
	super();
    }
    @Override
    public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", salary=" + salary + "]";
    }
}
