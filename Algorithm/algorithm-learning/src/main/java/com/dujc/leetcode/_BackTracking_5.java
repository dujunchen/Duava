package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_5 {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"));
        System.out.println(count);
    }

    public static String longestPalindrome(String s) {
        final char[] chars = s.toCharArray();
        ArrayList<Integer> path = new ArrayList<>();
        List<String> res = new ArrayList<>();
        backTracking(chars, 0, path, res);
        //再找出最长子串
        if (res.isEmpty()) {
            return s.substring(0, 1);
        }

        return res.get(0);
    }

    /**
     * 回溯
     *
     * @param chars      字符串
     * @param startIndex 下一轮组合的起始位置
     * @param path       截取区间的起始终止下标
     * @param res        存放所有回文子串
     */
    //统计递归次数 方便查看剪枝效果
    static int count = 0;
    private static void backTracking(char[] chars, int startIndex, ArrayList<Integer> path, List<String> res) {
        count++;
        if (startIndex > chars.length)
            return;
        if (path.size() == 2) {
            int left = path.get(0);
            int right = path.get(1);
            if (isPalindrome(left, right, chars)) {
                if (!res.isEmpty()) {
                    if (right - left + 1 > res.get(0).length()) {
                        res.clear();
                        StringBuilder str = new StringBuilder();
                        for (int i = left; i <= right; i++) {
                            str.append(chars[i]);
                        }
                        res.add(str.toString());
                    }
                } else {
                    StringBuilder str = new StringBuilder();
                    for (int i = left; i <= right; i++) {
                        str.append(chars[i]);
                    }
                    res.add(str.toString());
                }
            }
            return;
        }

        for (int i = startIndex; i < chars.length; i++) {
            path.add(i);
            backTracking(chars, i + 1, path, res);
            path.remove(path.size() - 1);
        }

    }

    //判断是否是回文串
    private static boolean isPalindrome(int left, int right, char[] chars) {
        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
