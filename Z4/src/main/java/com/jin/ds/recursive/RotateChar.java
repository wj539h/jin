package com.jin.ds.recursive;

public class RotateChar {
    public RotateChar(int[] a) {
        this.a = a;
    }

    int a[];

    public static void main(String[] args) {
        int temp[] = {0, 1, 2};
        RotateChar r = new RotateChar(temp);
        r.doRotate(temp.length - 1);
    }

    public void doRotate(int l) {
        if (l == 0)
            return;
        for (int i = 0; i <= l; i++) {
            doRotate(l - 1);
            //System.out.println(l);//输出9次，3个2，6个1, l=2的时候有3次，这3次调用递归每个l=1的时候都有2次，所以6+3是9次
            if (l == 1) print();
            changePos(l);

        }
    }

    public void changePos(int l) {
        int temp = a[l];
        for (int i = l; i > 0; i--) {
            a[i] = a[i - 1];
        }
        a[0] = temp;
    }

    public void print() {
        String msg = "";
        for (int e : a) {
            msg += e + " ";
        }
        System.out.println(msg);
    }
}
