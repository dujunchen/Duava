package com.dujc.leetcode;

import java.util.Arrays;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _TwoPointers_344 {
    public static void main(String[] args) {
        char[] s = "abcd".toCharArray();
        reverseString(s);
        System.out.println(Arrays.toString(s));
    }

    public static void reverseString(char[] s) {
        final int length = s.length;
        if (length <= 1)
            return;
        int head = 0;
        int tail = length - 1;
        while (head < tail) {
            char temp = s[head];
            s[head] = s[tail];
            s[tail] = temp;
            head++;
            tail--;
        }
    }

}
