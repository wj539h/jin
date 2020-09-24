package com.jin.java8.p1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import com.jin.java8.Employee;

/**
 * Lambda是一个匿名函数，可以把Lambda表达式理解为是一段可以传递的代码（将代码像数据一样进行传递）。
 * 可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。
 * @author Administrator
 *
 */

public class TestLambda {
  //原来的匿名内部类
    @Test
    public void test1(){
        Comparator<Integer> com=new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> ts=new TreeSet<>(com);
    }

    //Lambda表达式
//    @Test
    public void test2(){
        Comparator<Integer> com=(x,y)->Integer.compare(x,y);
        TreeSet<Integer> ts=new TreeSet<>((x,y)->Integer.compare(x,y));
    }

    List<Employee> employees=Arrays.asList(
            new Employee("张三",18,9496.2),
            new Employee("李四",52,2396.2),
            new Employee("王五",56,996.2),
            new Employee("赵六",8,94.2)
    );

//    @Test
    public void test3(){
        //需求：获取当前公司中员工年龄大于35的员工信息
        List<Employee> emps=filterEmplyees1(employees);
        for(Employee e:emps){
            System.out.println(e);
        }
        System.out.println("---------------------");

        //需求：获取当前公司中员工工资大于2000的员工信息
        List<Employee> emps2=filterEmplyees2(employees);
        for(Employee e:emps2){
            System.out.println(e);
        }
    }

    public List<Employee> filterEmplyees1(List<Employee> list){
        List<Employee> emps=new ArrayList<Employee>();
        for (Employee emp : list) {
            if(emp.getAge()>=35){
                emps.add(emp);
            }
        }
        return emps;
    }

    public List<Employee> filterEmplyees2(List<Employee> list){
        List<Employee> emps=new ArrayList<Employee>();
        for (Employee emp : list) {
            if(emp.getSalary()>=2000){
                emps.add(emp);
            }
        }
        return emps;
    }

//    @Test
    public void test4(){
        List<Employee> emps=filterEmplyees(employees,new FilterEmployeeByAge());
        for(Employee e:emps){
            System.out.println(e);
        }
        System.out.println("---------------------");
        List<Employee> emps2=filterEmplyees(employees,new FilterEmployeeBySalary());
        for(Employee e:emps2){
            System.out.println(e);
        }
    }

    //优化方式一：策略设计模式
    public List<Employee> filterEmplyees(List<Employee> list,MyPredicate<Employee> myPredicate){
        List<Employee> emps=new ArrayList<Employee>();
        for (Employee emp : list) {
            if(myPredicate.test(emp)){
                emps.add(emp);
            }
        }
        return emps;
    }

    //优化方式二：匿名内部类
//    @Test
    public void test5(){
        List<Employee> list=filterEmplyees(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee t) {
                return t.getSalary()>=2000;
            }
        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    //优化方式三：Lambda表达式
//    @Test
    public void test6(){
        List<Employee> list=filterEmplyees(employees, (e)->e.getSalary()>=2000);
        list.forEach(System.out::println);
    }

    //优化方式四：stream API
//    @Test
    public void test7(){
        employees.stream()
                 .filter((e)->e.getSalary()>=2000)
                 .forEach(System.out::println);

        System.out.println("------------------");

        employees.stream()
                 .map(Employee::getName)
                 .forEach(System.out::println);
    }
}
