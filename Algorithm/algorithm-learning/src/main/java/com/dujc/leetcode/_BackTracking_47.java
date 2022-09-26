package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_47 {
    public static void main(String[] args) {
        permuteUnique(new int[]{0, 0, 1, 1}).forEach(System.out::println);
        System.out.println(count);

    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        List<List<Integer>> res = new ArrayList<>();
        //去重之前必须先要排序
        Arrays.sort(nums);
        backTracking(nums, path, used, res);
        return res;
    }

    //统计递归次数 方便查看剪枝效果
    static int count = 0;

    private static void backTracking(int[] nums, ArrayList<Integer> path, boolean[] used, List<List<Integer>> res) {
        count++;
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        //排列问题因为有顺序，所以每次都从0开始遍历，只是把一些已使用过的元素跳过
        for (int i = 0; i < nums.length; i++) {
            //回溯的递归N叉树中 **同一层** 去重， 如果是 **同一个分支** 中去重，则条件改为 i>0 && nums[i-1] == nums[i] && used[i-1]
            if (i > 0 && nums[i - 1] == nums[i] && !used[i - 1])
                continue;

            // 跳过已选取的元素
//            if (path.contains(i)) {
//                continue;
//            }
            // 直接使用path.contains()遍历匹配效率较低，使用一个单独的数组用来记录已使用过的元素
            if (used[i])
                continue;
            used[i] = true;
            path.add(nums[i]);
            //每次都从0开始选取
            backTracking(nums, path, used, res);
            used[i] = false;
            path.remove(path.size() - 1);
        }

    }

}
