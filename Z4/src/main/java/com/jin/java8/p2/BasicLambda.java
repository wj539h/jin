package com.jin.java8.p2;

import java.util.Comparator;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * 一、Lambda表达式的基础语法 Java8中引入了一个新的操作符”->” 该操作符称为箭头操作符或Lambda操作符， 箭头操作符将Lambda表达式拆分成两部分： 左侧：Lambda
 * 表达式的参数列表 右侧：Lambda 表达式中所需执行的功能，即 Lambda 体
 * 
 * @author Administrator
 *
 */
public class BasicLambda {
    /**
     * 语法格式一：无参数，无返回值 ()->System.out.println(“Hello Lambda!”);
     */
//    @Test
    public void test1() {
	//通过匿名内部类的方式实现接口
	Runnable r = new Runnable() {
	    @Override
	    public void run() {
		System.out.println("Hello World!");
	    }
	};
	r.run();

	System.out.println("----------------------");
	//匿名内部类用代替匿名内部类
	Runnable r1 = () -> System.out.println("Hello Lambda!");
	r1.run();
    }

    /**
     * 语法格式二：有一个参数，并且无返回值 (x)->System.out.println(x); 
     * 语法格式三：若只有一个参数，小括号可以不写 x->System.out.println(x);
     */
//    @Test
    public void test2() {
	Consumer<String> con = (x) -> System.out.println(x);//对Consumer接口中有一个参数的accept方法的实现
	//x->System.out.println(x)
	con.accept("啦啦啦");
    }
    
    /**
     * 语法格式四：有两个以上的参数，有返回值，并且Lambda体中有多条语句
     */
    @Test
    public void test3(){
        Comparator<Integer> com=(x,y)->{
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }
    
    /**
     * 语法格式五：若Lambda体中只有一条语句，大括号和 return 都可以省略不写
     */
    @Test
    public void test4(){
        Comparator<Integer> com=(Integer x,Integer y)->Integer.compare(x,y);
    }
    
    /**
     * 语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，
     * 数据类型，即“类型推断” (Integer x,Integer y)->Integer.compare(x,y);
     * 左右遇一个参数括号省
     * 左侧推断类型省
     */
    @Test
    public void test5(){
        Comparator<Integer> com=(x,y)->Integer.compare(x,y);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
