package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Recursion_509_2 {
    public static void main(String[] args) {
        System.out.println(fib(40));
    }


    public static int fib(int n) {
        if(n == 0) return 0;
        if(n == 1) return 1;
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

}
