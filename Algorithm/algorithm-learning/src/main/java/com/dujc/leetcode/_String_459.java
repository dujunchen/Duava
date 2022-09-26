package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

class _String_459 {
    public static void main(String[] args) {
        System.out.println(repeatedSubstringPattern("abab"));
    }
    public static boolean repeatedSubstringPattern(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        //将原字符串翻倍，去掉首字符和尾字符
        char[] newChars = new char[len * 2 - 2];
        for(int i = 1; i < len;i++){
            newChars[i - 1] = chars[i];
        }
        for(int i = 0; i < len - 1;i++){
            newChars[i + len - 1] = chars[i];
        }

        return kmpSearch(newChars, chars);
    }

    public static boolean kmpSearch(char[] n, char[] m) {
        final int[] lcp = getLargestCommonPresuffix(m);
        List<Integer> res = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < n.length) {
            if (n[i] == m[j]) {
                i++;
                j++;
                if (j == m.length) {
                    return true;
                }
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lcp[j - 1];
                }
            }
        }
        return false;
    }


    /**
     * 计算子串m的最大公共前后缀数组
     *
     * @param m 子串m
     * @return lcp 最大公共前后缀数组
     */
    private static int[] getLargestCommonPresuffix(char[] m) {
        int i = 1;
        int j = 0;
        int len = m.length;
        int[] lcp = new int[len];
        while (i < len) {
            if (m[i] == m[j]) {
                lcp[i++] = ++j;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lcp[j - 1];
                }
            }
        }
        return lcp;
    }
}