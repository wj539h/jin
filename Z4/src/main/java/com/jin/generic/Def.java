package com.jin.generic;

public class Def {
    static Fruit f = new Fruit();static Fruit fArr[] = {new Fruit(), new Fruit(), new Fruit()};
    static Apple a = new Apple();static Apple aArr[] = {new Apple(), new Apple(), new Apple()};
    static Orange o = new Orange();static Orange oArr[] = {new Orange(), new Orange(), new Orange()};
    static Clothes c = new Clothes();static Clothes cArr[] = {new Clothes(), new Clothes(), new Clothes()};
    static Tshirt t = new Tshirt();static Tshirt tArr[] = {new Tshirt(), new Tshirt(), new Tshirt()};
    static Pants p = new Pants();static Pants pArr[] = {new Pants(), new Pants(), new Pants()};
    static Object obj = new Object();static Object objArr[] = {new Object(), new Object(), new Object()};
    static CharSequence ch_ar = new String();static CharSequence charArr[] = {new String(), new String(), new String()};
    static Box<Fruit> boxF = new Box<>();static Box<Apple> boxA = new Box<>();static Box<Orange> boxOr = new Box<>();
    static Box<Clothes> boxC = new Box<>();static Box<Tshirt> boxT = new Box<>();static Box<Pants> boxP = new Box<>();
    static Box<Object> boxObj = new Box<>();static Box<CharSequence> boxChar = new Box<>();
    static void initArr(){
        boxF.elArr =fArr;boxA.elArr = aArr;boxOr.elArr = oArr;
        boxC.elArr =cArr;boxT.elArr = tArr;boxP.elArr = pArr;
        boxObj.elArr=objArr;boxChar.elArr=charArr;
    }
}
class Person {public String toString() {return "Im Person";}}
class Worker extends Person {public String toString() {return "Im worker";}}
class Doctor extends Person {public String toString() {return "Im doctor";}}

class Fruit {public String toString() {return "Im Fruit";}}
class Apple extends Fruit {public String toString() {return "Im Apple";}}
class Orange extends Fruit {public String toString() {return "Im Orange";}}

class Clothes{public String toString() {return "Im Clothes";}}
class Tshirt extends Clothes{public String toString() {return "Im Tshirt";}}
class Pants extends Clothes{public String toString() {return "Im Pants";}}