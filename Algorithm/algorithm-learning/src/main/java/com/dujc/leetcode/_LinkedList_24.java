package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/8/23
 */
public class _LinkedList_24 {
    public static void main(String[] args) {
        ListNode node4 = new ListNode(4, null);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        System.out.println(swapPairs(node1));
    }

    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            //后面 cur.next 和cur.next.next 指针要发生改变，所以这两个节点提前先保存下来
            final ListNode temp1 = cur.next;
            final ListNode temp2 = cur.next.next.next;
            cur.next = cur.next.next;
            cur.next.next = temp1;
            temp1.next = temp2;
            cur = cur.next.next;
        }
        return dummy.next;
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
