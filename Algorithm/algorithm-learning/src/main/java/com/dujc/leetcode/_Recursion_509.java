package com.dujc.leetcode;

import java.util.HashMap;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Recursion_509 {
    public static void main(String[] args) {
        System.out.println(fib(40));
    }

    static HashMap<Integer, Integer> cache = new HashMap<>();

    public static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int n1;
        int n2;
        if (cache.containsKey(n - 1)) {
            n1 = cache.get(n - 1);
        } else {
            n1 = fib(n - 1);
            cache.put(n - 1, n1);
        }
        if (cache.containsKey(n - 2)) {
            n2 = cache.get(n - 2);
        } else {
            n2 = fib(n - 2);
            cache.put(n - 2, n2);
        }
        return n1 + n2;
    }
}
