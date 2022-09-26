package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _String_541 {
    public static void main(String[] args) {
        System.out.println(reverseStr("abcd", 2));
    }

    public static String reverseStr(String s, int k) {
        final int _2k = 2 * k;
        final char[] chars = s.toCharArray();
        final int length = chars.length;
        int p = 0;
        while (length - p >= _2k) {
            reverseStr(chars, p, p + k - 1);
            p = p + _2k;
        }

        if (length - p < k) {
            reverseStr(chars, p, length - 1);
        } else {
            reverseStr(chars, p, k - 1 + p);
        }
        return new String(chars);
    }

    public static void reverseStr(char[] chars, int i, int j) {
        while (i < j) {
            if (chars[i] != chars[j]) {
                char t = chars[i];
                chars[i] = chars[j];
                chars[j] = t;
            }
            i++;
            j--;
        }
    }

}
