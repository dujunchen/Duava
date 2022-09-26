package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_46 {
    public static void main(String[] args) {
        permute(new int[]{0,0,1,1}).forEach(System.out::println);
        System.out.println(count);

    }

    public static List<List<Integer>> permute(int[] nums) {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        List<List<Integer>> res = new ArrayList<>();
        backTracking(nums, path, used, res);
        return res;
    }

    //统计递归次数 方便查看剪枝效果
    static int count = 0;

    private static void backTracking(int[] nums, ArrayList<Integer> path, boolean[] used, List<List<Integer>> res) {
        count++;
        if (path.size() == nums.length) {
            List<Integer> item = new ArrayList<>(path.size());
            for (Integer p : path) {
                item.add(nums[p]);
            }
            res.add(item);
            return;
        }
        //排列问题因为有顺序，所以每次都从0开始遍历，只是把一些已使用过的元素跳过
        for (int i = 0; i < nums.length; i++) {
            // 跳过已选取的元素
//            if (path.contains(i)) {
//                continue;
//            }
            // 直接使用path.contains()遍历匹配效率较低，使用一个单独的数组用来记录已使用过的元素
            if (used[i])
                continue;
            used[i] = true;
            path.add(i);
            //每次都从0开始选取
            backTracking(nums, path, used, res);
            used[i] = false;
            path.remove(path.size() - 1);
        }

    }

}
