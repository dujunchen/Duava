package com.dujc.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Stack_503 {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(nextGreaterElement(new int[]{2, 1, 2, 4, 3})));
    }

    public static int[] nextGreaterElement(int[] nums) {
        final int length = nums.length;
        int[] ans = new int[length];
        Stack<Integer> stack = new Stack<>();
        //使用%模拟出一个虚拟的两倍大小的数组，然后在新的数组上再使用单调栈查找，这样比实际构造一个两倍数组节约一倍空间，以及数组元素拷贝的开销
        for (int i = length * 2 - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i % length] >= stack.peek()) {
                stack.pop();
            }
            if (i < length) {
                ans[i] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(nums[i % length]);
        }
        return ans;
    }

}
