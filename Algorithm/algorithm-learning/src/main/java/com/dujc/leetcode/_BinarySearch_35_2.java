package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BinarySearch_35_2 {
    public static void main(String[] args) {

        System.out.println(searchInsert(new int[]{1, 3, 5}, 4));
    }

    public static int searchInsert(int[] nums, int target) {
        int head = 0;
        int tail = nums.length - 1;
        while (head <= tail) {
            int mid = head + (tail - head) / 2;
            if (target == nums[mid]) {
                return mid;
            }
            if (target > nums[mid]) {
                  head = mid + 1;
            }
            if (target < nums[mid]) {
                tail = mid - 1;
            }
        }
        return  head;
    }
}
