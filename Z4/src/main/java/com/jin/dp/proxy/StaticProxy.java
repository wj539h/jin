package com.jin.dp.proxy;

import java.util.HashMap;
import java.util.Map;

public class StaticProxy {
    public static void main(String[] args) {
	/*Man man = new Man();
	Human logStaticProxy = new ManLogProxy(man);
	logStaticProxy.say();
	logStaticProxy.say("fffffffffff");
	Human timeStaticProxy = new ManTimeProxy(man);
	timeStaticProxy.say();
	timeStaticProxy.say("fffffffffff");*/
	Map map = new HashMap();
	map.put(1, "1");
	map.put(17, "170");
	map.put(33, "330");
	map.put(49, "490");
	map.put(65, "650");
	map.put(81, "810");
	map.put(97, "970");
	map.put(113, "1130");
	map.put(129, "1290");
	map.put(145, "1450");
	map.put(161, "1610");
	map.put(177, "1770");
	map.put(193, "1930");
	map.put(209, "2090");
	map.put(241, "2410");
	map.put(257, "2570");
	
	/*map.put(17, "2323");
	map.put(1, "10000");
	map.put(17, "2323");
	map.put(1, "10000");
	map.put(17, "2323");
	map.put(1, "10000");
	map.put(17, "2323");*/
	System.out.println(map);
    }
}

interface Human {
    public void say();

    public void say(String str);
}

class Man implements Human {
    public void say() {
	System.out.println("I m man, say nothing");
    }

    public void say(String str) {
	System.out.println("I m man, saying : " + str);
    }
}

class ManLogProxy implements Human {
    public ManLogProxy(){
	if(man == null){
	    man = new Man();
	}
    }
    public ManLogProxy(Man man) {
	this.man = man;
    }

    private Man man;

    public void say() {
	System.out.println("before say()");
	man.say();
	System.out.println("after say()");
    }

    public void say(String str) {
	System.out.println("before say("+str+")");
	man.say(str);
	System.out.println("after say("+str+")");
    }
}

class ManTimeProxy implements Human {
    public ManTimeProxy(){
	if(man == null){
	    man = new Man();
	}
    }
    public ManTimeProxy(Man man) {
	this.man = man;
    }

    private Man man;

    public void say() {
	long startTime = System.nanoTime();
	man.say();
	long endTime = System.nanoTime();
	System.out.println("say() takes : "+(endTime-startTime));
    }

    public void say(String str) {
	long startTime = System.nanoTime();
	man.say(str);
	long endTime = System.nanoTime();
	System.out.println("say("+str+") takes : "+(endTime-startTime));
    }
}