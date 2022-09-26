package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _TwoPointers_141 {
    public static void main(String[] args) {

    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode quick = head;
        while (quick != null && quick.next != null) {
            quick = quick.next.next;
            slow = slow.next;
            if (slow == quick)
                return true;
        }
        return false;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
