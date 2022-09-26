package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Tree_94_2 {
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
        if (root.left != null) {
            values.addAll(inorderTraversal(root.left));
        }
        values.add(root.val);
        if (root.right != null) {
            values.addAll(inorderTraversal(root.right));
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
