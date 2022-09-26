package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_22_2 {
    public static void main(String[] args) {
        generateParenthesis(10).forEach(System.out::println);
        System.out.println(count);

    }

    public static List<String> generateParenthesis(int n) {
        // 生成一个[(((...)))]的数组，然后使用回溯法生成所有的排列情况，这个问题就转换为 含重复元素的全排列 问题
        char[] parenthesis = new char[2 * n];
        for (int i = 0; i < n; i++) {
            parenthesis[i] = '(';
        }
        for (int i = n; i < 2 * n; i++) {
            parenthesis[i] = ')';
        }

        ArrayList<Character> path = new ArrayList<>();
        boolean[] used = new boolean[parenthesis.length];
        List<String> res = new ArrayList<>();
        backTracking(parenthesis, path, used, res);
        return res;
    }

    //统计递归次数 方便查看剪枝效果
    static int count = 0;

    private static void backTracking(char[] parenthesis, ArrayList<Character> path, boolean[] used, List<String> res) {
        count++;
        if (path.size() == parenthesis.length) {
            if (validParenthesis(path)) {
                StringBuilder sb = new StringBuilder();
                for (Character c : path) {
                    sb.append(c);
                }
                res.add(sb.toString());
            }
            return;
        }
        //排列问题因为有顺序，所以每次都从0开始遍历，只是把一些已使用过的元素跳过
        for (int i = 0; i < parenthesis.length; i++) {
            //回溯的递归N叉树中 **同一层** 去重， 如果是 **同一个分支** 中去重，则条件改为 i>0 && parenthesis[i-1] == parenthesis[i] && used[i-1]
            if (i > 0 && parenthesis[i - 1] == parenthesis[i] && !used[i - 1])
                continue;

            // 跳过已选取的元素
//            if (path.contains(i)) {
//                continue;
//            }
            // 直接使用path.contains()遍历匹配效率较低，使用一个单独的数组用来记录已使用过的元素
            if (used[i])
                continue;
            used[i] = true;
            path.add(parenthesis[i]);
            //每次都从0开始选取
            backTracking(parenthesis, path, used, res);
            used[i] = false;
            path.remove(path.size() - 1);
        }

    }

    //判断括号串是否合法  条件 遍历括号串，只要有一个地方 出现 右括号数量>左括号数量，则说明不合法
    private static boolean validParenthesis(ArrayList<Character> path) {
        int leftCount = 0;
        int rightCount = 0;
        for (Character c : path) {
            if (rightCount > leftCount)
                return false;
            if (c == '(')
                leftCount++;
            if (c == ')')
                rightCount++;
        }
        return true;
    }

}
