package com.jin.dp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static void main(String[] args) {
	Human man = new Man();
	System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
	//InvocationHandler ih = new LogProxyHandler(man);
	InvocationHandler ih = new TimeProxyHandler(man);
	Human manProxy = (Human)Proxy.newProxyInstance(Human.class.getClassLoader(), new Class[]{Human.class}, ih);
	manProxy.say("testD");
    }
    
    interface Human {
	public void say();

	public void say(String str);
    }

    static class Man implements Human {
	public void say() {
	    System.out.println("I m man, say nothing");
	}

	public void say(String str) {
	    System.out.println("I m man, saying : " + str);
	}
    }
    
    static class LogProxyHandler implements InvocationHandler {
	protected Human man;

	public LogProxyHandler() {
	    if (man == null) {
		man = new Man();
	    }
	}

	public LogProxyHandler(Human man) {
	    this.man = man;
	}

	//唯一不好理解的就是这个proxy，其实很简单，就是代理出来的那个类,就是上面的那个dyProxy
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    System.out.println("before say()");
	    //可以试试((Human)proxy).say(),输出的结果是无限的before say()，相当于自己的这个invoke方法
	    Object obj = method.invoke(man, args);
	    System.out.println("after say()");
	    return obj;
	}
    }

    static class TimeProxyHandler extends LogProxyHandler {

	public TimeProxyHandler(Human man) {
	    super(man);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    long startTime = System.nanoTime();
	    Object obj = super.invoke(proxy, method, args)/*method.invoke(super.man, args)*/;
	    long endTime = System.nanoTime();
	    System.out.println("say() takes : " + (endTime - startTime));
	    return obj;
	}
    }

}





