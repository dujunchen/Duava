package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Recursion_206 {
    public static void main(String[] args) {
        ListNode node3 = new ListNode(3);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        System.out.println(reverseList(node1));
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        if (head.next == null) {
            return head;
        }
        //这边会一直递归到最后一个Node，才会返回，返回的就是最后一个节点，即反转后的head节点
        final ListNode last = reverseList(head.next);
        //只有等递归结束后才会走到这里，同时每次递归返回的都是原始链表最后一个节点，也就是新的链表的head节点
        //将每一个节点下一个节点的next指向自己
        head.next.next = head;
        //将自己的next清空，在前一个节点的递归中会在上一行代码中给自己的next赋值
        head.next = null;
        return last;
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
