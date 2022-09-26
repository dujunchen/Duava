package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_131 {
    public static void main(String[] args) {
        partition("aab").forEach(System.out::println);
        System.out.println(count);
    }

    public static List<List<String>> partition(String s) {
        final char[] chars = s.toCharArray();
        ArrayList<String> path = new ArrayList<>();
        List<List<String>> res = new ArrayList<>();
        backTracking(chars, 0, path, res);
        return res;
    }


    //统计递归次数 方便查看剪枝效果
    static int count = 0;

    private static void backTracking(char[] chars, int startIndex, ArrayList<String> path, List<List<String>> res) {
        count++;
        //切割到字符串最后，就是一种切割方案
        if (startIndex == chars.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i < chars.length; i++) {
            //判断是不是回文串，不是的话，切割线继续往后移动
            if (!isPalindrome(startIndex, i, chars)) {
                continue;
            }
            StringBuilder validStr = new StringBuilder();
            for (int j = startIndex; j <= i; j++) {
                validStr.append(chars[j]);
            }
            path.add(validStr.toString());
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
