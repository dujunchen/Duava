package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Recursion_344 {
    public static void main(String[] args) {
    }

    public static void reverseString(char[] s) {
        doReverse(s, 0, s.length - 1);
    }

    private static void doReverse(char[] s, int left, int right) {
        if (left >= right)
            return;
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        doReverse(s, ++left, --right);
    }
}
