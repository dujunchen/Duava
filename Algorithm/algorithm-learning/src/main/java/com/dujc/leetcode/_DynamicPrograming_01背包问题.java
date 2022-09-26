package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _DynamicPrograming_01背包问题 {
    public static void main(String[] args) {
        /**
         * 物品 重量 价值
         *  0    2   15
         *  1    3   20
         *  2    4   30
         * 背包最大承重 4
         */
        int[] weights = {2, 3, 4};
        int[] values = {15, 20, 30};
        /**
         * i表示物品 j表示背包最大承重 dp[i][j]的值表示 从[0,i]中任取元素放入最大承重为j的背包中可以获取的最大价值
         */
        int[][] dp = new int[3][5];
        //先把所有元素初始化为-1
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = -1;
            }
        }
        //如果背包最大承重j=0，自然所有的物品i都放不进去
        for (int i = 0; i <= 2; i++) {
            dp[i][0] = 0;
        }
        //物品i=0一行的元素初始化取决于物品0的重量weights[0]和背包最大承重j的大小，如果weights[0]>j，则背包无法装下，则元素值=0，否则能装下，元素值=weights[0]
        for (int j = 1; j <= 4; j++) {
            if (weights[0] > j) {
                dp[0][j] = 0;
            } else {
                dp[0][j] = values[0];
            }
        }

        int max = -1;
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 4; j++) {
                //不放i物品 情况
                int notInclude = dp[i - 1][j];
                //放i物品 情况
                // 如果 背包最大承重 >= 物品i的重量，才有可能放i物品
                if (j - weights[i] >= 0) {
                    //这一行递推关系是整个问题的核心，也是最难理解的地方
                    // 从[0,i]中任取物品放入最大承重为j的背包中可以获取的最大价值=[0,i-1]中任取物品放入最大承重为(j-物品i重量)的背包中可以获取的最大价值+物品i的重量
                    int include = dp[i - 1][j - weights[i]] + values[i];
                    dp[i][j] = Math.max(notInclude, include);
                } else {
                    //如果 背包最大承重 < 物品i的重量，则无法放i物品
                    dp[i][j] = notInclude;
                }

                if (dp[i][j] > max) {
                    max = dp[i][j];
                }
            }
        }
        System.out.println(max);
    }

}
