package com.dujc.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _TwoPointers_1 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{3, 3}, 6)));
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        //排序之前先把原始数组各元素的索引保存起来，排序后索引会发生变化
        Map<Integer, Integer> num2Index = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (num2Index.containsKey(nums[i]) && nums[i] * 2 == target) {
                res[0] = i;
                res[1] = num2Index.get(nums[i]);
                return res;
            } else {
                num2Index.put(nums[i], i);
            }
        }
        Arrays.sort(nums);
        int head = 0;
        int tail = nums.length - 1;
        while (head < tail) {
            final int sum = nums[head] + nums[tail];
            if (sum == target) {
                res[0] = num2Index.get(nums[head]);
                res[1] = num2Index.get(nums[tail]);
                return res;
            } else if (sum > target) {
                tail--;
            } else {
                head++;
            }
        }
        return res;
    }

}
