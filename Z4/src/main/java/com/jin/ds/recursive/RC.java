package com.jin.ds.recursive;

import java.util.Arrays;
import java.util.Stack;

public class RC {

    public static void main(String[] args) {
        //new RC().display(arr.length);
        new RC().displayNoRecur(arr.length);
    }

    static char[] arr = new char[]{'a','b','c','d'};
    static Stack<Integer> s = new Stack<>();
    public void displayNoRecur(int n){
        s.push(1);
        s.push(2);
        s.push(3);

        //int c = s.peek();
        for(int i=n;i>0;i--) {
            int tem = n;
            s.push(n-1);

        }
    }

    public void display(int n){
        if(n==1)
            return;
        for (int i=n;i>0;i--) {
            display(n-1);
            if(n==2)
                System.out.println(Arrays.toString(arr));
            rotate(n);
        }
    }

    private void rotate(int n) {
        char temp = arr[0];
        for(int i=1;i<n;i++) {
            arr[i-1]=arr[i];
        }
        arr[n-1] = temp;
    }
}
