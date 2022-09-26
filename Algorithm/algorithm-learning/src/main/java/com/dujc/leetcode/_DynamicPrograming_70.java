package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _DynamicPrograming_70 {
    public static void main(String[] args) {

        System.out.println(climbStairs(3));
    }

    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] dp = new int[2];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 1; i <= n - 2; i++) {
            int sum = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = sum;
        }
        return dp[1];
    }
}
