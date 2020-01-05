package com.Algorithms;

import java.util.ArrayDeque;
import java.util.LinkedList;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTree {

    TreeNode root = null;

    void addNodeSorted(int newData) {
        TreeNode newNode = new TreeNode(newData);
        if (root == null) {
            root = newNode;
            return;
        }
        addNodeSortedRec(root, newNode);
    }

    private void addNodeSortedRec(TreeNode current, TreeNode newNode) {
        if (newNode.data <= current.data) {
            if (current.left != null)
                addNodeSortedRec(current.left, newNode);
            else
                current.left = newNode;
        } else {
            if (current.right != null)
                addNodeSortedRec(current.right, newNode);
            else
                current.right = newNode;
        }
    }

    boolean contains(int value) {
        if (root == null) {
            return false;
        }
        return containsRec(root, value);
    }

    private boolean containsRec(TreeNode current, int value) {
        if (current.data == value)
            return true;
        if (value < current.data) {
            if (current.left != null)
                return containsRec(current.left, value);
            else
                return false;
        } else {
            if (current.right != null)
                return containsRec(current.right, value);
            else
                return false;
        }
    }

    int heightRec(TreeNode root) {
        if (root == null)
            return 0;
        int leftHeight = heightRec(root.left);
        int rightHeight = heightRec(root.right);
        int bigger = Math.max(leftHeight, rightHeight);
        return bigger + 1;
    }

    int heightIterative() {
        int leftHeight = 1, rightHeight = 1;
        if (root == null)
            return -1;
        TreeNode node = root;
        while (node.left != null) {
            leftHeight += 1;
            node = node.left;
        }
        node = root;
        while (node.right != null) {
            rightHeight += 1;
            node = node.right;
        }
        return Math.max(leftHeight, rightHeight);
    }

    void preOrder(TreeNode node) {
        if (node != null) {
            System.out.println(node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    void inOrder(TreeNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.data);
            inOrder(node.right);
        }
    }

    void postOrder(TreeNode node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.data);
        }
    }

    void inOrderIterative(TreeNode node) {
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.println(node.data);
                node = node.right;
            }
        }
    }

    void levelOrder(TreeNode node) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.remove();

            System.out.println(treeNode.data);

            if (treeNode.left != null) {
                queue.add(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
            }
        }
    }

    void printLeafNodes(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            printLeafNodes(node.left);
        }
        if (node.right != null) {
            printLeafNodes(node.right);
        }
        if (node.left == null && node.right == null) {
            System.out.println(node.data);
        }
    }

    void printLeafNodesIteratively(TreeNode root) {
        if (root == null) {
            return;
        }
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left == null && node.right == null) {
                System.out.printf("%d ", node.data);
            }
        }
    }
}