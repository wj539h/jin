package com.jin.ds.recursive;

//https://www.cnblogs.com/zhouthanos/p/3807495.html
public class DictionaryPermutation {
    private char[] data = new char[]{'b','a','c','d'};
    private int length = data.length;

    public void permutate(String input) {
        System.out.println(data);
        while (nextPermutate()) {
            System.out.println(data);
        }
    }

    private boolean nextPermutate() {
        int end = length - 1;
        int swapPoint1 = end, swapPoint2 = end;
        // the actual swap-point is swapPoint1 - 1
        while (swapPoint1 > 0 && data[swapPoint1] <= data[swapPoint1 - 1])
            swapPoint1--;
        if (swapPoint1 == 0)
            return false;
        else {
            while (swapPoint2 > 0 && data[swapPoint2] <= data[swapPoint1 - 1])
                swapPoint2--;
            swap(data, swapPoint1 - 1, swapPoint2);
            reverse(data, swapPoint1, end);
            return true;
        }
    }

    private void swap(char[] data, int left, int right) {
        char temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

    private void reverse(char[] data, int left, int right) {
        for (int i = left, j = right; i < j; i++, j--)
            swap(data, i, j);
    }

    public static void main(String... args) {
        DictionaryPermutation p = new DictionaryPermutation();
        p.permutate("abcd");
    }
}
