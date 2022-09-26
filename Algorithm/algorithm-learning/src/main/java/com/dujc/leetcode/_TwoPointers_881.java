package com.dujc.leetcode;

import java.util.Arrays;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _TwoPointers_881 {
    public static void main(String[] args) {
        System.out.println(numRescueBoats(new int[]{3,5,3,4}, 5));
    }

    public static int numRescueBoats(int[] people, int limit) {
        int res = 0;
        Arrays.sort(people);
        int head = 0;
        int tail = people.length - 1;
        while (head <= tail) {
            if (head == tail) {
                res++;
                return res;
            }
            final int x = limit - people[tail];
            if (x == 0) {
                res++;
                tail--;
            } else {
                // x肯定>0
                if (people[head] <= x) {
                    res++;
                    head++;
                    tail--;
                } else {
                    res++;
                    tail--;
                }
            }
        }
        return res;
    }

}
