package com.dujc.leetcode;

import java.util.HashSet;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _SlidingWindow_1456 {
    public static void main(String[] args) {
        System.out.println(maxVowels("novowels", 1));
    }

    private static boolean isVowel(HashSet<Character> vowels, char c) {
        return vowels.contains(c);
    }

    public static int maxVowels(String s, int k) {
        //当判断目标集合数量很大时，用Hashset优化
        HashSet<Character> vowels = new HashSet<>(5);
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        final char[] chars = s.toCharArray();
        int left = 0;
        int max = 0;
        for (int i = 0; i < k; i++) {
            if (isVowel(vowels, chars[i]))
                max++;
        }
        if (max == k) {
            return max;
        }
        int temp = max;
        while (left + k - 1 < chars.length - 1) {
            if (isVowel(vowels, chars[left])) {
                temp--;
            }
            left++;
            if (isVowel(vowels, chars[left + k - 1])) {
                temp++;
            }
            if (temp > max) {
                if (temp == k) {
                    return temp;
                }
                max = temp;
            }
        }
        return max;
    }
}
