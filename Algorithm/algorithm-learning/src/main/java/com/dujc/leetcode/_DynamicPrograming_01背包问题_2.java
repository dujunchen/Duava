package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _DynamicPrograming_01背包问题_2 {
    public static void main(String[] args) {
        /**
         * 物品 重量 价值
         *  0    2   15
         *  1    3   20
         *  2    4   30
         * 背包最大承重 4
         */
        int[] weights = {1, 3, 4};
        int[] values = {15, 20, 30};
        /**
         * i表示背包最大承重 dp[i]的值表示 任取元素放入最大承重为i的背包中可以获取的最大价值
         */
        int[] dp = new int[5];

        int max = -1;
        //先遍历物品，再遍历背包
        for (int i = 0; i <= 2; i++) {
            //之所以需要倒序遍历，是因为 递推公式中，当前计算的值是依赖于数组左边上一轮计算的结果的，在二维数组中是在左上方，如果正序遍历就有可能会覆盖之前计算的值，所以需要倒序遍历
            for (int j = 4; j >= weights[i]; j--) {
                //不放i物品 情况
                int notInclude = dp[j];
                //放i物品 情况
                int include = dp[j - weights[i]] + values[i];
                dp[j] = Math.max(notInclude, include);

                if (dp[j] > max) {
                    max = dp[j];
                }
            }
        }
        System.out.println(max);
    }

}
