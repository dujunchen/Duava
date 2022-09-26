package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BinarySearch_704_1 {
    public static void main(String[] args) {

        System.out.println(searchInsert(new int[]{1, 3, 5}, 5));
    }

    public static int searchInsert(int[] nums, int target) {
        int head = 0;
        int tail = nums.length - 1;
        while (head <= tail) {
            //防止大数相加 溢出
            int mid = head + (tail - head) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                head = mid + 1;
            }
            if (nums[mid] > target) {
                tail = mid - 1;
            }
        }
        return -1;
    }
}
