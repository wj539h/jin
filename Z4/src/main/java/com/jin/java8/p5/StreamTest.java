package com.jin.java8.p5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.junit.Test;

import com.jin.java8.Employee;

public class StreamTest {
    //创建Stream
    @Test
    public void test1(){
        //1.可以通过Collection 系列集合提供的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过 Arrays 中的静态方法stream()获取数组流
        Employee[] emps=new Employee[10];
        Stream<Employee> stream2=Arrays.stream(emps);

        //3.通过Stream 类中的静态方法of()
        Stream<String> stream3=Stream.of("aa","bb","cc");
        
        //4.创建无限流
        //迭代
        Stream<Integer> stream4=Stream.iterate(0, (x) -> x+2);
        stream4.limit(10).forEach(System.out::println);

        //5.生成
        Stream.generate(() -> Math.random())
              .limit(5)
              .forEach(System.out::println);
        
        //6.构建
        Builder<Integer> builder = Stream.builder();
        Stream<Integer> stream6 = builder.build();
        stream6.forEach(System.out::println);
    }
    
    
    
    /** 中间操作
	多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理！
	而在终止操作时一次性处理，成为“惰性求值”。
	１．筛选与切片 
     *  filter--接收Lambda，从流中排除某些元素。
     *  limit--截断流，使其元素不超过给定数量。
     *  skip(n)--跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n) 互补
     *  distinct--筛选，通过流所生成元素的 hashCode() 和 equals() 去掉重复元素
     */
    List<Employee> employees = Employee.init1();
    //内部迭代：迭代操作由 Stream API 完成
    @Test
    public void test2(){
        //中间操作：不会执行任何操作
        Stream<Employee> stream=employees.stream()
                                .filter((e) -> e.getAge()>35 );
        //终止操作：一次性执行全部内容，即 惰性求值
        stream.forEach(System.out::println);
    }
    //外部迭代
    @Test
    public void test3(){
        Iterator<Employee> it=employees.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    @Test
    public void test4(){//发现“短路”只输出了两次，说明只要找到 2 个 符合条件的就不再继续迭代
        employees.stream()
                 .filter((e)->{
                     System.out.println("短路！");
                     return e.getSalary()>5000;
                 })
                 .limit(2)
                 .forEach(System.out::println);
    }

    @Test
    public void test5(){
        employees.stream()
                 .filter((e)->e.getSalary()>5000)
                 .skip(2)//跳过前两个
                 .distinct()//去重，注意：需要Employee重写hashCode 和 equals 方法
                 .forEach(System.out::println);
    }
}
