package com.jin.thread;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jin.Const.*;
public class TestUnsafe implements Runnable{
    public Person p;
    private int count = 100000;
    public static Unsafe UNSAFE = null;
    public static long OFFSET;
    static {
        //Caused by: java.lang.SecurityException: Unsafe, 因为classLoader对于自己定义的类是AppClassLoader
        //对于JDK的所有类,classloader是Bootstrap,所以是空

        //UNSAFE = Unsafe.getUnsafe(); //这样获取会报上面的错,原因在上面

        try {
            //这样获得就可以了
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe)field.get(null);


            OFFSET = UNSAFE.objectFieldOffset(Person.class.getDeclaredField("i"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        //calc();
        //calcAto();
        calcUnsafe();
    }

    public void calcAto() {
        for (int i = 0; i < count; i++) {
            pln(tName()+" -- "+(p.ai.getAndIncrement()));
        }
    }

    public void calc() {
        for (int i = 0; i < count; i++) {
            pln(tName()+" -- "+(p.i));
            p.i++;
        }
    }

    public void calcUnsafe() {
        for (int i = 0; i < count; i++) {
            boolean result = UNSAFE.compareAndSwapInt(p,OFFSET,p.i,p.i+1);
            if(result) {
                pln(tName()+" -- "+(p.i));
            } else {
                pln(tName()+" !!!-- "+(p.i));
            }
        }
    }

    public static void main(String[] args) {
        TestUnsafe tu = new TestUnsafe();
        Person p=new Person();
        tu.p = p;
        for(int i=0;i<2;i++) {
            new Thread(tu).start();
        }
    }

    private static class Person {
        public int i;
        public AtomicInteger ai = new AtomicInteger(0);
    }
}
