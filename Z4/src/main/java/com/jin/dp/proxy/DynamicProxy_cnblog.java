package com.jin.dp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用动态代理的五大步骤
1.通过实现InvocationHandler接口来自定义自己的InvocationHandler;
 
2.通过Proxy.getProxyClass获得动态代理类
 
3.通过反射机制获得代理类的构造方法，方法签名为getConstructor(InvocationHandler.class)
 
4.通过构造函数获得代理对象并将自定义的InvocationHandler实例对象传为参数传入
 
5.通过代理对象调用目标方法
 * @author Administrator
 *
 */

public class DynamicProxy_cnblog {
    public interface IHello{
        void sayHello();
        String toCapital(String oriStr);
    }
    static class Hello implements IHello{
        public void sayHello() {
            System.out.println("Hello world!!");
        }
        
        public String toCapital(String oriStr) {
            System.out.println("oriStr : "+oriStr);
            return oriStr.toUpperCase();
        }
    }
    //自定义InvocationHandler
    static  class HWInvocationHandler implements InvocationHandler{
        //目标对象
        private Object target;
        public HWInvocationHandler(Object target){
            this.target = target;
        }
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName()+"------插入前置通知代码-------------");
            //执行相应的目标方法//com.jin.dp.proxy.DynamicProxy$HWInvocationHandler@3339ad8e
            Object rs = method.invoke(target,args);
            System.out.println(method.getName()+"------插入后置处理代码-------------");
            return rs;
        }
    }
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
	/**---------第一种方式----------**/
	//生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //获取动态代理类
        //Class proxyClazz = Proxy.getProxyClass(IHello.class.getClassLoader(),IHello.class);
        //获得代理类的构造函数，并传入参数类型InvocationHandler.class
        //Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        //通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        //IHello iHello = (IHello) constructor.newInstance(new HWInvocationHandler(new Hello()));
        //通过代理对象调用目标方法
        //iHello.sayHello();
        	
        /**---------第二种方式----------**/
        //生成$Proxy0的class文件
       // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello  ihello = (IHello) Proxy.newProxyInstance(
        	IHello.class.getClassLoader(),  //加载接口的类加载器
                new Class[]{IHello.class},      //一组接口
                new HWInvocationHandler(new Hello())); //自定义的InvocationHandler
        System.out.println(ihello.getClass());
        ihello.sayHello();
        ihello.toCapital("wangjin");
    }
    
    
}