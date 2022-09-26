package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _HashTable_389 {
    public char findTheDifference(String s, String t) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 97]--;
        }
        for (char c : t.toCharArray()) {
            counts[c - 97]++;
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0)
                return (char) (i + 97);
        }
        return 0;
    }
}
