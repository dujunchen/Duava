package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _TwoPointers_18 {
    public static void main(String[] args) {
        fourSum(new int[]{1000000000, 1000000000, 1000000000, 1000000000
        }, -294967296).forEach(System.out::println);
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        // 排序一方面是为了更方便剪枝去重
        // 另一方面是 方便使用双指针查找
        Arrays.sort(nums);
        for (int a = 0; a < len; a++) {
            //对a剪枝
            if (nums[a] > target && nums[a] >=0) {
                break;
            }
            //对a去重
            if (a > 0 && nums[a] == nums[a - 1]) {
                continue;
            }
            for (int b = a + 1; b < len; b++) {
                //对 b 元素 去重
                if (b > a + 1 && nums[b] == nums[b - 1]) {
                    continue;
                }
                int left = b + 1;
                int right = len - 1;

                while (left < right) {
                    int v = nums[a] + nums[b] + nums[left] + nums[right];
                    if (v == target) {
                        //要考虑 nums[a] + nums[b] + nums[left] + nums[right]过于大 导致int溢出变成负数的情况
                        res.add(Arrays.asList(nums[a], nums[b], nums[left], nums[right]));
                        left++;
                        right--;
                        //对left元素去重
                        while (left < right && nums[left] == nums[left - 1]) {
                            left++;
                        }
                        //对right元素去重
                        while (left < right && nums[right] == nums[right + 1]) {
                            right--;
                        }
                    } else if (v > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }

        }

        return res;
    }
}
