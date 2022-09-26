package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _String_5 {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("cbbd"));
    }

    public static String longestPalindrome(String s) {
        final char[] chars = s.toCharArray();
        final int length = chars.length;
        int maxLen = 0;
        int maxStartIndex = 0;
        for (int i = 0; i < length; i++) {
            int left = i;
            int right = i;
            while (chars[left] == chars[right] && ++right < length) {
            }
            right--;
            while (left >= 0 && right < length) {
                if (chars[left] == chars[right]) {
                    left--;
                    right++;
                } else {
                    break;
                }
            }
            int curLen = right - left - 1;
            if (curLen > maxLen) {
                maxLen = curLen;
                maxStartIndex = left + 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLen; i++) {
            sb.append(chars[i + maxStartIndex]);
        }
        return sb.toString();
    }
}
