package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _SlidingWindow_209_2 {
    public static void main(String[] args) {
        System.out.println(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }

    public static int minSubArrayLen(int target, int[] nums) {
        final int length = nums.length;
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while (right < length) {
            sum += nums[right++];
            while (sum >= target) {
                if (right - left < minLen) {
                    minLen = right - left;
                    // 存在的情况下，最小也就1了
                    if (minLen == 1) {
                        return 1;
                    }
                }
                sum -= nums[left++];
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
