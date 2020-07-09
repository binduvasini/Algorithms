package com.Algorithms;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
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

    /**
     * Merge k sorted lists.
     * Solve using Min Heap.
     *
     * @param lists
     * @return
     */
    public Node mergeKLists(Node[] lists) {
        if (lists.length == 0)
            return null;

        PriorityQueue<Node> minHeap = new PriorityQueue<>((o1, o2) -> o1.data - o2.data);
        Node merged = new Node(0);
        Node head = merged;

        for (Node l : lists) {
            if (l != null)
                minHeap.add(l);
        }

        while (minHeap.size() > 0) {
            Node smallest = minHeap.remove();
            merged.next = smallest;
            if (smallest.next != null)
                minHeap.add(smallest.next);
            merged = merged.next;
        }

        return head.next;
    }

    /**
     * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node position and not the value in the nodes.
     * Input: 2->1->3->5->6->4->7->NULL
     * Output: 2->3->6->7->1->5->4->NULL
     *
     * @param head
     * @return
     */
    public Node oddEvenList(Node head) {
        if (head == null)
            return null;
        Node odd = head, even = head.next;
        Node evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * Given a linked list, swap every two adjacent nodes and return its head.
     * You may not modify the values in the list's nodes, only nodes itself may be changed.
     * Example:
     *
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     * @param head
     * @return
     */
    public Node swapPairs(Node head) {
        Node newHead = new Node(0, head);
        Node current = newHead;
        while (current.next != null && current.next.next != null) {
            current.next = swap(current.next, current.next.next);
            current = current.next.next;
        }
        return newHead.next;
    }

    private Node swap(Node node1, Node node2) {
        node1.next = node2.next;
        node2.next = node1;
        return node2;
    }
}

