package com.Algorithms;

import java.util.LinkedList;

public class BSTIterator {
    LinkedList queue;

    public BSTIterator(TreeNode root) {
        queue = new LinkedList<>();
        inOrder(root);
    }

    private void inOrder(TreeNode node) {
        if (node == null)
            return;
        inOrder(node.left);
        queue.add(node.data);
        inOrder(node.right);
    }

    public int next() {
        int nextVal = (int) queue.getFirst();
        queue.removeFirst();
        return nextVal;
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
