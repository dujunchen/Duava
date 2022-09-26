package com.dujc.leetcode;

import java.util.Arrays;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Array_59 {
    public static void main(String[] args) {
        for (int[] matrix : generateMatrix(5)) {
            System.out.println(Arrays.toString(matrix));
        }
    }

    public static int[][] generateMatrix(int n) {
        int half = n / 2;
        int[][] arr = new int[n][n];
        int offset = 0;
        int value = 1;
        //总共需要循环打印 n/2 圈
        for (int k = 1; k <= half; k++) {
            //每一圈中每条边遍历的终点位置
            final int last = n - 1 - offset;
            for (int j = offset; j < last; j++) {
                arr[offset][j] = value++;
            }
            for (int i = offset; i < last; i++) {
                arr[i][last] = value++;
            }
            for (int j = last; j > offset; j--) {
                arr[last][j] = value++;
            }
            for (int i = last; i > offset; i--) {
                arr[i][offset] = value++;
            }
            offset++;
        }
        //如果n是奇数，最后会在[half][half]位置留下一个空格，需要手动填充
        if (n % 2 == 1) {
            arr[half][half] = n * n;
        }
        return arr;
    }

}
