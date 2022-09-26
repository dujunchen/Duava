package com.dujc.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Stack_496 {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> num2NextGreaterElement = new HashMap<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                num2NextGreaterElement.put(stack.pop(), num);
            }
            stack.push(num);
        }
        while (!stack.isEmpty()) {
            num2NextGreaterElement.put(stack.pop(), -1);
        }
        //再遍历nums1，从hashmap中获取每一个元素的下一个最大元素
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = num2NextGreaterElement.get(nums1[i]);
        }
        return ans;
    }

}
