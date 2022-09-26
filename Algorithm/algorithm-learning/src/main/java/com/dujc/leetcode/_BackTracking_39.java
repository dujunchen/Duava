package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_39 {
    public static void main(String[] args) {
        System.out.println(combinationSum(new int[]{5, 3, 7, 6, 2}, 8));
        System.out.println(count);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        //先排序方便剪枝
        Arrays.sort(candidates);
        backTracking(candidates, target, 0, res, path, 0);
        return res;
    }
    //统计递归次数 方便查看剪枝效果
    static int count = 0;

    private static void backTracking(int[] candidates, int target, int startIndex, List<List<Integer>> res,
                                     LinkedList<Integer> path, int sum) {
        count++;
        //因为都是正整数，只会越加越大，如果某一刻和已经大于target了。后面再递归加下去只会越来越大，不可能在符合等于target了
        if (sum > target) {
            return;
        }
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        //剪枝
        int maxIndex = candidates.length;
        for (int i = startIndex; i < candidates.length; i++) {
            //遇到第一个已经超过target的元素，后面不需要再算了，肯定都是超了
            if (candidates[i] + sum > target) {
                maxIndex = i;
            }
        }

        for (int i = startIndex; i < maxIndex; i++) {
            path.push(candidates[i]);
            sum += candidates[i];
            backTracking(candidates, target, i, res, path, sum);
            sum -= candidates[i];
            path.pop();
        }

    }
}
