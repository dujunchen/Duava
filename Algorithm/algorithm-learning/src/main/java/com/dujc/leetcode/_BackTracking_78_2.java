package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_78_2 {
    public static void main(String[] args) {
        subsets(new int[]{1, 2, 3, 4}).forEach(System.out::println);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(Collections.emptyList());
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> temp = new ArrayList<>();
            for (List<Integer> list : res) {
                final ArrayList<Integer> arrayList = new ArrayList<>(list);
                arrayList.add(nums[i]);
                temp.add(arrayList);
            }
            res.addAll(temp);
        }
        return res;
    }

}

