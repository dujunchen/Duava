package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/8/23
 */
public class _LinkedList_203 {
    public static void main(String[] args) {
        ListNode node7 = new ListNode(7);
        ListNode node6 = new ListNode(7,node7);
        ListNode node5 = new ListNode(7,node6);
        ListNode node4 = new ListNode(7, node5);
        ListNode node3 = new ListNode(7,node4 );
        ListNode node2 = new ListNode(7, node3);
        ListNode node1 = new ListNode(7, node2);
        System.out.println(removeElements(node1, 7));
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode cur = head;
        ListNode prev = null;
        ListNode newHead = head;
        while (cur != null) {
            if (cur.val == val) {
                if (prev == null) {
                    newHead =  cur.next;
                    cur = cur.next;
                }else{
                    prev.next = cur.next;
                    cur = cur.next;
                }
            }else{
                prev = cur;
                cur = cur.next;
            }
        }
        return newHead;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

}
