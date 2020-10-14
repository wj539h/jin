package com.jin.ds.stack;
import java.util.Stack;

public class NumberSystemConversion {
    enum SHIFT {
        BINARY, OCTNORY, HEX
    }
    /**
     * 从十进制转换到其他进制。
     * @param number 要转换的十进制数。
     * @param shift 目标数制。
     * @return
     */
    public static String conversion(Integer number, SHIFT shift) {
        StringBuilder res = new StringBuilder();
        Stack stack = new Stack(); // 新建一个栈来存放十进制数每次除二时的余数。
        while (number != 0) {
            switch (shift) {
                case BINARY:
                    stack.push((number % 2));
                    number /= 2;
                    break;
                case OCTNORY:
                    stack.push((number % 8));
                    number /= 8;
                    break;
                case HEX:
                    stack.push((number % 16));
                    number /= 16;
                    break;
            }
        }
        // 弹出栈顶元素，即逆序输出余数
        while (!stack.empty()) {
            res.append(stack.pop());
        }
        return res.toString();
    }
}
