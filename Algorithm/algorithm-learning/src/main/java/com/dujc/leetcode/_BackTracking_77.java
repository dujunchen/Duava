package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_77 {
    public static void main(String[] args) {
        combine(4, 2).forEach(System.out::println);
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        backTracking(n, k, 1, path, res);
        return res;
    }

    /**
     * @param n
     * @param k
     * @param startIndex 每一个递归中的循环开始位置
     * @param path       临时存放递归收集过程中的合法组合
     * @param res        最终结果集
     */
    private static void backTracking(int n, int k, int startIndex, LinkedList<Integer> path, List<List<Integer>> res) {
        // 将栈中合法的结果放入res
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        //每一轮递归中的循环
        //剪枝优化
        int maxIndex = n-(k-path.size())+1;
        for (int i = startIndex; i <= maxIndex; i++) {
            path.push(i);
            backTracking(n, k, i + 1, path, res);
            path.pop();
        }
    }
}
