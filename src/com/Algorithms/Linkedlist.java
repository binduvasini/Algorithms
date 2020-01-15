package com.Algorithms;

import java.util.ArrayDeque;

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

    boolean hasCycle(Node head) {
        if (head == null || head.next == null)
            return false;

        Node slowPointer = head;
        Node fastPointer = head.next;

        while (slowPointer != fastPointer) {
            if (fastPointer == null || fastPointer.next == null)
                return false;
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }
        return true;
    }

    Node getIntersectionNode(Node headA, Node headB) {
        //Two pointers to traverse list A and list B respectively and swap when one of them reaches the end of its list.
        Node pointerA = headA;
        Node pointerB = headB;

        while (pointerA != pointerB) {
            pointerA = (pointerA == null) ? headB : pointerA.next;
            pointerB = (pointerB == null) ? headA : pointerB.next;
        }
        return pointerA;
    }

    boolean isPalidrome(Node head) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        Node curr = head;

        while (curr != null) {
            stack.push(curr.data);
            curr = curr.next;
        }

        while (head != null) {
            int nodeFromStack = stack.pop();
            if (nodeFromStack != head.data) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    Node headCopy;

    boolean isPalindromeRecursive(Node head) {
        headCopy = head;
        return isPalindromeRec(head);
    }

    boolean isPalindromeRec(Node curr) {
        if (curr == null) {
            return true;
        }
        boolean isEqual = isPalindromeRec(curr.next);
        boolean isPalindrome = headCopy.data == curr.data;
        headCopy = headCopy.next;
        return isEqual && isPalindrome;
    }

    public Node addTwoNumbers(Node l1, Node l2) {
        Node resultHead = null;
        Node result = null;
        Node sumNode;
        int carry = 0, sum;

        while (l1 != null || l2 != null) {
            sum = carry + ((l1 != null) ? l1.data : 0) + ((l2 != null) ? l2.data : 0);

            carry = sum / 10;

            sum = sum % 10;
            sumNode = new Node(sum);

            if (resultHead == null)
                resultHead = sumNode;
            else
                result.next = sumNode;
            result = sumNode;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry > 0) {
            result.next = new Node(carry);
        }
        return resultHead;
    }

}
