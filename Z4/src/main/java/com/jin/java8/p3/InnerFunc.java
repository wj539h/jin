package com.jin.java8.p3;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * 
 * java内置四大函数式接口
 * 
 * Consumer<T> :消费型接口
 *          void accept(T t);
 * 
 * Supplier<T> :供给型接口
 *          T get();
 * 
 * Function<T,R> :函数型接口
 *          R apply(T t);
 * 
 * Predicate<T> :断言型接口
 *          boolean test(T t);
 *
 */

public class InnerFunc {
  //Consumer<T> 消费型接口：
//    @Test
    public void test1(){
	happy("Jin", (m)->{System.out.println("hello : "+m);});
    }
    
    private void happy(String name, Consumer<String> co) {
	co.accept(name);
    }

    //Supplier<T> 供给型接口:
    //需求：产生指定个数的整数，并放入集合中
    @Test
    public void test2(){
	int n = 8;
	List<Integer> list = produceInt(n, () -> {return new Double(Math.random()*100).intValue();});
	System.out.println(list);
	assertEquals(n, list.size()); 
    }
    
    private List<Integer> produceInt(int n, Supplier<Integer> su) {
	List<Integer> list=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Integer temp=su.get();
            list.add(temp);
        }
        return list;
    }

    //Function<T,R> 函数型接口:
//    @Test
    public void test3(){
        String newStr=strHandler("\t\t\t 啦啦啦德玛西亚  ", (str)->str.trim());
        System.out.println(newStr);

        String subStr=strHandler("无与伦比，为杰沉沦", (str)->str.substring(5,9));
        System.out.println(subStr);
    }
    //需求：处理字符串
    public String strHandler(String str,Function<String,String> fun){
        return fun.apply(str);
    }

    //Predicate<T> 断言型接口：
//    @Test
    public void test4(){
        List<String> list=Arrays.asList("Hello","jj","Lambda","www","ok");
        List<String> strList=filterStr(list, (s)->s.length()>3);
        for (String string : strList) {
            System.out.println(string);
        }
    }
    //需求：将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list,Predicate<String> pre){
        List<String> strList=new ArrayList<>();
        for ( String str : list) {
            if(pre.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }
}
