package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Tree_144 {
    public static void main(String[] args) {

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
//        TreeNode treeNode3 = new TreeNode(3);
        treeNode1.right = treeNode2;
//        treeNode2.left = treeNode3;
        System.out.println(preorderTraversal(null));
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        if (root == null) {
            return values;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final TreeNode pop = stack.pop();
            values.add(pop.val);
            if (pop.right != null)
                stack.push(pop.right);
            if (pop.left != null)
                stack.push(pop.left);
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
