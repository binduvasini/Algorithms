package com.Algorithms;

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

public class Linkedlist {
    Node head = null;
    Node tail = null;
    int size = 0;

    public void addFirst(int newData) {
        size += 1;
        if (head == null) {
            head = new Node(newData);
            tail = head;
            return;
        }
        Node newNode = new Node(newData);
        newNode.next = head;
        head = newNode;

    }

    public void addLast(int newData) {
        size += 1;
        Node newLastNode = new Node(newData);
        if (head == null) {
            head = new Node(newData);
            return;
        }
        Node current = head;
        Node lastnode = null;
        while (current != null) {
            lastnode = current;
            current = current.next;
        }
        lastnode.next = newLastNode;
        tail = newLastNode;
    }

    Node reverseList(Node head) {
        Node curr = head;
        Node prev = null;
        while (curr != null) {
            Node temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        head = prev;
        return head;
    }

    Node reverseListRec(Node head) {
        return reverseListRec(head, null);
    }

    Node reverseListRec(Node curr, Node prev) {
        if (curr == null)
            return prev;
        Node temp = curr.next;
        curr.next = prev;
        return reverseListRec(temp, curr);
    }
}

