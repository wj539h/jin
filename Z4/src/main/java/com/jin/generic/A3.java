package com.jin.generic;

public class A3 {
    public static void main(String[] args) {
        IFDemoImpl<String> ifi = new IFDemoImpl<String>();
        ifi.show(Long.valueOf(123));
        ifi.gogogo("Jin");
    }
}
interface IFDemo<T> {
    void show(T t);
}
class IFDemoImpl<E> implements IFDemo<Long> {
    public void gogogo(E e) {
        System.out.println("gogogo --- "+e);
    }
    public void show(Long l) {
        System.out.println(l);
    }
}