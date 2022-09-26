package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _String_KMP {
    public static void main(String[] args) {
        System.out.println(kmpSearch("bababa".toCharArray(), "abab".toCharArray()));
    }

    /**
     * KMP算法查找子串
     *
     * @param n
     * @param m
     * @return
     */
    public static List<Integer> kmpSearch(char[] n, char[] m) {
        final int[] lcp = getLargestCommonPresuffix(m);
        List<Integer> res = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < n.length) {
            if (n[i] == m[j]) {
                i++;
                j++;
                if (j == m.length) {
                    res.add(i - j);
                    j = lcp[j - 1];
                }
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lcp[j - 1];
                }
            }
        }
        return res;
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
