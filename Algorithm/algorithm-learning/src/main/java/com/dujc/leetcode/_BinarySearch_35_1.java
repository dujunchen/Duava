package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BinarySearch_35_1 {
    public static void main(String[] args) {

        System.out.println(searchInsert(new int[]{1, 3, 5}, 4));
    }

    public static int searchInsert(int[] nums, int target) {
        int head = 0;
        int tail = nums.length - 1;
        while (true) {
            //如果tail和head相邻或者重合，特殊处理
            if (tail - head <= 1) {
                if (nums[head] == target) {
                    return head;
                }
                if (nums[tail] == target) {
                    return tail;
                }
                //元素不存在，确认插入位置
                if (target < nums[head]) {
                    if (head < 1) {
                        return 0;
                    } else {
                        // 上一个元素是 head-1，当前元素应该在head-1 +1位置
                        return head;
                    }
                } else if (target > nums[head] && target < nums[tail]) {
                    return head + 1;
                } else {
                    return tail + 1;
                }
            }
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
    }
}
