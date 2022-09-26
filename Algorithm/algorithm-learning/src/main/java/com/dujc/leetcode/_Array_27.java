package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Array_27 {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2));
    }

    public static int removeElement(int[] nums, int val) {
        final int length = nums.length;
        int px = 0;
        int py = 0;
        while (px < length) {
            if (nums[px] == val) {
                py = px + 1;
                while (py < length && nums[py] == val) {
                    py++;
                }
                if (py == length) {
                    break;
                }
                nums[px] = nums[py];
                nums[py] = val;
            }
            px++;
        }
        return px;
    }
}
