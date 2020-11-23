package com.jin.generic;

import static com.jin.Const.*;

class Demo<BB> {
    public <T> void show(T t) {
        pln("show :" + t);
    }
    public <W> W print(W w) {
        pln("print:" + w);
        return w;
    }
    public void display(BB bb) {
        pln("display:" + bb);
    }
}
public class A1 {
    public static void main(String[] args) {
        Demo<Doctor> de = new Demo<>();
        Doctor d = new Doctor();
        Worker w = new Worker();
        de.show("abzc");
        pln(de.print(w));
        de.print(3);
        de.display(d);
    }
}
