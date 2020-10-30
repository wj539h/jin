package com.jin.ds.list;

import com.jin.Const;

public class LinkedList {
    private Node head;
    private int len = 0;
    private static class Node {
        int d;
        Node next;
        Node(int d){
            this.d = d;
        }

        @Override
        public String toString() {
            return "Node("+ d +")";
        }
    }

    //尾插, 非哨兵
    public void tail(int d) {
        Node n = new Node(d);
        if(head == null) {
            head = n;
        } else {
            Node tmp = head;
            while(tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = n;
        }
        len++;
    }

    //sentinelAdd, 尾插, 哨兵实现
    public void sTailAdd(int d) {
        if(head == null) {
            head = new Node(-1);
        }
        Node n = new Node(d);
        Node tmp = head;
        while(tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = n;
        len++;
    }

    //头插, 非哨兵
    public void firstAdd(int d) {
        Node n = new Node(d);
        if(head != null) {
            n.next = head;
        }
        head = n;
        len++;
    }
    //头插, 哨兵
    public void sFirstAdd(int d) {
        if(head == null) {
            head = new Node(-1);
        }
        Node n = new Node(d);
        n.next = head.next;
        head.next = n;
        len++;
    }

    public Node find(int d) {
        Node n = head;
        while(n != null && n.d != d) {
            n = n.next;
        }
        Const.pln("Find : "+n);
        return n;
    }
    //删除
    public void del(int d) {
        Node n = head;
        Node prev = null;
        while(n != null && n.d != d) {
            prev = n;
            n = n.next;
        }
        if(n != null) {
            prev.next = n.next;
            n = null;
            len--;
        }else{
            Const.pln("Not Find : "+new Node(d));
        }
    }

    //递归反转, 只是用哨兵方式插入
    public Node invertRecur(Node n) {
        if (n.next == null) {
            return n;
        }
        Node retN = invertRecur(n.next);
        if (retN.next == null) {
            head.next.next = null;
            head.next = retN;
        }
        retN.next = n;
        return n;
    }

    //迭代反转, 只是用哨兵方式插入
    public void invertIter() {
        Node pre = head.next;
        Node cur = pre.next;
        pre.next = null;
        while(cur != null) {
            Node tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        head.next = pre;
    }

    //O(1)的效率删除指定节点
    public void delSingleO1(int d) {
        Node n = find(d);
        n.d = n.next.d;
        n.next = n.next.next;
        len--;
    }

    //部分反转
    public void invertTest1(int from, int to) {
        Node fromPre = null;
        Node toNext = null;
        Node pre = head;
        Node cur = pre.next;
        while(cur!=null){
            if(cur.d==from) {
                fromPre = pre;
            }
            if(cur.d==to) {
                toNext = cur.next;
                break;
            }
            pre = cur;
            cur=cur.next;
        }
        //Const.pln(fromPre.toString()+toNext);
        pre = fromPre.next;
        cur = pre.next;
        pre.next = toNext;
        while(true) {
            if(toNext == null || cur.d != toNext.d) { //处理 from是第一个元素和to是最后一个元素
                Node tmp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = tmp;
                if(tmp == null || (toNext != null && cur.d == toNext.d)) //处理 from是第一个元素和to是最后一个元素
                    break;
            }
        }
        fromPre.next = pre;
    }

    //部分反转
    public void invertTest2(int k) {
        Node fromPre = null;
        Node toNext = null;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addTail(list);
        list.display();
        //list.find(33);
        //list.del(88);
        //list.delSingleO1(12);
        //list.invertIter();
        //list.invertTest1(33, 3);
        //list.invertTest2(4);
        list.display();
    }

    public void display() {
        Const.pln("head : " + head + " --- "+"len : "+len);
        if (head != null) {
            Node tmp = head.d>0? head:head.next; //>0 not sentinel, else sentinel
            while (tmp != null) {
                if(tmp.d == head.d || (head.d<=0 && head.next.d==tmp.d))
                    Const.p("Display : "+tmp + "-->");
                else if(tmp.next == null)
                    Const.p( tmp );
                else
                    Const.p(tmp + "-->");
                tmp = tmp.next;
            }
        }
        Const.pln();
    }
    public void addFirst(LinkedList list) {
        addFirst(list,false);
    }
    public void addFirst(LinkedList list, boolean sentinel) {
        if(sentinel) {
            list.sFirstAdd(7);list.sFirstAdd(22);list.sFirstAdd(1);
            list.sFirstAdd(12);list.sFirstAdd(55);list.sFirstAdd(10);
            list.sFirstAdd(64);list.sFirstAdd(33);list.sFirstAdd(3);
            list.sFirstAdd(100);list.sFirstAdd(5);list.sFirstAdd(26);
        }else{
            list.firstAdd(7);list.firstAdd(22);list.firstAdd(1);
            list.firstAdd(12);list.firstAdd(55);list.firstAdd(10);
            list.firstAdd(64);list.firstAdd(33);list.firstAdd(3);
            list.firstAdd(100);list.firstAdd(5);list.firstAdd(26);
        }
    }


    public void addTail(LinkedList list, boolean sentinel) {
        if(sentinel) {
            list.sTailAdd(7);list.sTailAdd(22);list.sTailAdd(1);
            list.sTailAdd(12);list.sTailAdd(55);list.sTailAdd(10);
            list.sTailAdd(64);list.sTailAdd(33);list.sTailAdd(3);
            //list.sTailAdd(100);list.sTailAdd(5);list.sTailAdd(26);
        }else{
            list.tail(7);list.tail(22);list.tail(1);
            list.tail(12);list.tail(55);list.tail(10);
            list.tail(64);list.tail(33);list.tail(3);
            list.tail(100);list.tail(5);list.tail(26);
        }
    }
    public void addTail(LinkedList list) {
        addTail(list,true);
    }
}
