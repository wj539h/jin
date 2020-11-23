package com.jin.generic;

import java.util.Arrays;

import static com.jin.Const.*;

public class B1<TB1> {
    void replaceEle(Box<? super TB1> box, TB1 tb1, int n) {
        if(n==0)
            box.replaceFirst(tb1);
        else if(n==1)
            box.replaceSecond(tb1);
    }
    public static void main(String[] args) {
        Def.initArr();
        B1<String> b1 = new B1();
        b1.replaceEle(Def.boxChar, "String", 0);
    }
}
class Box<T> {
    public T elArr[];
    public void listAll() {
        pln(Arrays.toString(elArr));
    }

    public void replaceFirst(T t) {
        elArr[0] = t;
    }
    public void replaceSecond(T t) {
        elArr[1] = t;
    }
    public T t;
    public T getEle() {return t;}
    public void putEle(T t) {
        this.t=t;
    }
}