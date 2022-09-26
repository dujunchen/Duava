package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Array_485 {
    public static void main(String[] args) {
        System.out.println(findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}));
    }

    static int findMaxConsecutiveOnes(int[] arr) {
        int currentCount = 0;
        int maxCount = 0;

        for (int i : arr) {
            if (i == 1) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                }
                currentCount = 0;
            }
        }
        if (currentCount > maxCount) {
            maxCount = currentCount;
        }
        return maxCount;
    }
}
