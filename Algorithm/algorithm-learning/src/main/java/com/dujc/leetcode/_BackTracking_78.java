package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_78 {
    public static void main(String[] args) {
        subsets(new int[]{0,1, 2}).forEach(System.out::println);
    }


    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        backtrack(nums, 0, res, path);
        return res;
    }

    public static void backtrack(int[] nums, int startIndex, List<List<Integer>> res, LinkedList<Integer> path) {
        res.add(new ArrayList<>(path));
        for (int i = startIndex; i < nums.length; i++) {
            path.push(nums[i]);
            backtrack(nums, i + 1, res, path);
            path.pop();
        }
    }
}

