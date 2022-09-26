package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Tree_94 {
    public static void main(String[] args) {

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
//        TreeNode treeNode3 = new TreeNode(3);
        treeNode1.right = treeNode2;
//        treeNode2.left = treeNode3;
        System.out.println(inorderTraversal(null));
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        if (root == null) {
            return values;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        //Stack底层使用Vector实现，效率较低，替换为LinkedList
//        Stack<TreeNode> stack = new Stack<>();
        TreeNode temp = root;
        //一开始无脑把所有左节点入栈
        while (temp != null) {
            stack.push(temp);
            temp = temp.left;
        }
        //然后一个个出栈 节点，然后一旦遇到节点有右子节点，继续无脑把它所有左节点入栈
        while (!stack.isEmpty()) {
            final TreeNode pop = stack.pop();
            values.add(pop.val);
            if (pop.right != null) {
                temp = pop.right;
                while (temp != null) {
                    stack.push(temp);
                    temp = temp.left;
                }
            }
        }
        return values;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


}
