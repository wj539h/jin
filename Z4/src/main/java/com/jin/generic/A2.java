package com.jin.generic;

import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {
        GenericClass<Worker> w = new GenericClass<Worker>();
        w.setObject(new Worker());
        Worker worker = w.getObject();

        GenericClass<Integer> i = new GenericClass<Integer>();
        i.setObject(123);
        int in = i.getObject();

        GenericClass<List> l = new GenericClass<List>();
        l.setObject(new ArrayList());
        List list = l.getObject();
    }
}
class GenericClass<QQ> {
    private QQ q;
    public void setObject(QQ q) {
        this.q = q;
    }
    public QQ getObject() {
        return q;
    }
}
