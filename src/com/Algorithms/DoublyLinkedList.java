package com.Algorithms;

public class DoublyLinkedList {
    static class Node {
        int val;
        Node prev;
        Node next;

        public Node(int val) {
            this.val = val;
            prev = null;
            next = null;
        }
    }
    static Node head;

    /**
     * Write a function that turns a doubly linked list into a balanced binary tree.
     * 1 - 2 - 3 - 4 - 5 - 6

     *        4
     *    2        6
     *  1   3    5
     *
     * @return root of the tree
     */
    public static Node constructBT() {
        int n = countNodes(head);

        return constructBTRec(n);
    }

    public static Node constructBTRec(int n) {
        if (n <= 0) {
            return null;
        }

        Node left = constructBTRec(n / 2);
        Node root = new Node(head.val);

        root.prev = left;
        head = head.next;

        root.next = constructBTRec(n - (n / 2) - 1);

        return root;
    }

    private static int countNodes(Node head) {
        Node pointer = head;
        int count = 0;

        while (pointer != null) {
            count += 1;
            pointer = pointer.next;
        }

        return count;
    }

    public static void main(String[] args) {
        Node element1 = new Node(1);
        Node element2 = new Node(2);
        Node element3 = new Node(3);
        Node element4 = new Node(4);
        Node element5 = new Node(5);
        Node element6 = new Node(6);

        element1.prev = null;
        element1.next = element2;
        element2.prev = element1;
        element2.next = element3;
        element3.prev = element2;
        element3.next = element4;
        element4.prev = element3;
        element4.next = element5;
        element5.prev = element4;
        element5.next = element6;
        element6.prev = element5;
        element6.next = null;

        head = element1;

        Node root = constructBT();

        preOrder(root);
    }

    private static void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val);
        preOrder(node.prev);
        preOrder(node.next);
    }
}