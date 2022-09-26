package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Tree_94_2_temp {
    public static void main(String[] args) {

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        treeNode1.right = treeNode2;
        treeNode2.left = treeNode3;
        inorderTraversal(treeNode1);
    }

    public static void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            inorderTraversal(root.left);
        }
        System.out.println(root.val);
        if (root.right != null) {
            inorderTraversal(root.right);
        }
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
