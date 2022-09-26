package com.dujc.leetcode;

import java.util.Arrays;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Array_283 {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        int i = 0; //放置非0元素下标
        int j = 0;  //游标
        final int length = nums.length;
        while (j < length) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
            j++;
        }
        while (i < length) {
            nums[i++] = 0;
        }
    }


}
