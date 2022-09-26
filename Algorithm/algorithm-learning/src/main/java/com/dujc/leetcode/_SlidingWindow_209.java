package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _SlidingWindow_209 {
    public static void main(String[] args) {
        System.out.println(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }

    public static int minSubArrayLen(int target, int[] nums) {
        final int length = nums.length;
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = nums[left];
        while (right < length - 1) {
            //右节点移动
            while (right < length - 1 && sum < target) {
                right++;
                sum += nums[right];
            }
            //左节点移动
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                if (minLen == 1) {
                    return 1;
                }
                sum -= nums[left];
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE? 0 : minLen;
    }
}
