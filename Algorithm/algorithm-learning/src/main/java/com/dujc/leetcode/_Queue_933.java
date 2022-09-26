package com.dujc.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author dujc
 * @date 2022/8/23
 */
public class _Queue_933 {
    public static void main(String[] args) {
        RecentCounter recentCounter = new RecentCounter();
        System.out.println(recentCounter.ping(1));     // requests = [1]，范围是 [-2999,1]，返回 1
        System.out.println(recentCounter.ping(100));   // requests = [1, 100]，范围是 [-2900,100]，返回 2
        System.out.println(recentCounter.ping(3001));  // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
        System.out.println(recentCounter.ping(3002));  // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
    }


    private static class RecentCounter {
        Queue<Integer> queue = new LinkedList<Integer>();

        public RecentCounter() {

        }

        public int ping(int t) {
            queue.add(t);
            while (queue.size() > 0 && t - queue.peek() > 3000)
                queue.poll();
            return queue.size();
        }
    }
}

