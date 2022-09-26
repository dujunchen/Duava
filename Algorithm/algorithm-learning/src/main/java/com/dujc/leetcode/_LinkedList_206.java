package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/8/23
 */
public class _LinkedList_206 {
    public static void main(String[] args) {
//        ListNode node7 = new ListNode(7);
//        ListNode node6 = new ListNode(6, node7);
//        ListNode node5 = new ListNode(5, node6);
//        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        System.out.println(reverseList(node1));
    }

    public static ListNode reverseList(ListNode head) {
        ListNode temp;
        ListNode cur = head;
        ListNode prev = null;
        while (cur != null) {
            //将当前节点的下一个节点保存一下，下面要修改next指针
            temp = cur.next;
            //修改next指针指向prev
            cur.next = prev;
            //更新prev cur指针
            prev = cur;
            cur = temp;
        }
        return prev;
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
