package com.Algorithms;

import java.util.*;

public class Linkedlist {
    static class Node {
        int data;
        Node next;
        Node prev;
        Node child;

        public Node(int data) {
            this.data = data;
        }
        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

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

    public Node reverseList(Node head) {
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

    public Node reverseListRec(Node head) {
        return reverseListRec(head, null);
    }

    private Node reverseListRec(Node curr, Node prev) {
        if (curr == null)
            return prev;
        Node temp = curr.next;  //Keep hold of the next element.
        curr.next = prev;  //Reverse the pointer.
        return reverseListRec(temp, curr);
    }

    public boolean hasCycle(Node head) {
        if (head == null || head.next == null)
            return false;

        Node slow = head;
        Node fast = head.next;

        while (slow != fast) {
            if (fast == null || fast.next == null)
                return false;
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public Node getIntersectionNode(Node headA, Node headB) {
        //Two pointers to traverse list A and list B respectively. Swap when one of them reaches the end of its list.
        Node pointerA = headA;
        Node pointerB = headB;

        while (pointerA != pointerB) {
            pointerA = (pointerA == null) ? headB : pointerA.next;
            pointerB = (pointerB == null) ? headA : pointerB.next;
        }
        return pointerA;
    }

    public boolean isPalindrome(Node head) {
        Deque<Integer> stack = new ArrayDeque<>();
        Node curr = head;

        while (curr != null) {
            stack.push(curr.data);
            curr = curr.next;
        }

        while (head != null) {
            int val = stack.pop();
            if (val != head.data) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    Node headCopy;

    public boolean isPalindromeRecursive(Node head) {
        headCopy = head;
        return isPalindromeRec(head);
    }

    private boolean isPalindromeRec(Node curr) {
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

            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (carry > 0) {
            result.next = new Node(carry);
        }
        return resultHead;
    }

    public Node mergeTwoLists(Node list1, Node list2) {
        Node merged = new Node(-1);
        Node curr = merged;  // A pointer to point to the merged list.

        while (list1 != null && list2 != null) {
            // Compare the current nodes of both lists
            if (list1.data <= list2.data) {
                curr.next = list1;  // Attach list1's node to the merged list
                list1 = list1.next; // Move list1 pointer forward
            }
            else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        // Attach the remaining nodes to the merged list
        if (list1 != null) {
            curr.next = list1;
        }
        if (list2 != null) {
            curr.next = list2;
        }

        return merged.next;
    }

    /**
     * Merge k sorted lists.
     * Solve using Min Heap.
     *
     * @param lists
     * @return
     */
    public Node mergeKLists(Node[] lists) {
        Queue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.data));
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
                minHeap.add(smallest.next);  //These lists are linked. So we need to add the next elements here.
            merged = merged.next;
        }

        return head.next;
    }

    /**
     * Sort a linked list in increasing order.
     *
     * @param head
     * @return
     */
    public Node sortList(Node head) {
        if (head == null || head.next == null)
            return head;

        //Divide the list into two halves
        Node slow = head, fast = head;
        Node mid = null;

        while (fast != null && fast.next != null) {
            mid = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        mid.next = null;  //Separating the left half from the right half. TLE if this is not done.

        //sort each half
        Node l1 = sortList(head);
        Node l2 = sortList(slow);  //slow points at the middle position

        //merge l1 and l2
        return mergeKLists(new Node[]{l1, l2});  //Use the mergeKLists method.
        // It can be tweaked to just take the 2 lists as arguments.
    }

    /**
     * Dutch national flag problem as a Linked List.
     * @param head
     * @return
     */
    public Node sortColorList(Node head) {
        if (head == null || head.next == null)
            return head;

        // Create three empty nodes to point to the beginning of three linked lists.
        // These empty nodes are created to avoid many null checks.
        Node zeroList = new Node(0);
        Node oneList = new Node(0);
        Node twoList = new Node(0);

        // Initialize the current pointers for these three lists and the original list.
        Node zero = zeroList, one = oneList, two = twoList;
        Node curr = head;

        while (curr != null) {
            if (curr.data == 0) {
                zero.next = curr;
                zero = zero.next;
            }
            else if (curr.data == 1) {
                one.next = curr;
                one = one.next;
            }
            else {
                two.next = curr;
                two = two.next;
            }
            curr = curr.next;
        }

        // Merge the three lists
        zero.next = oneList.next;
        one.next = twoList.next;
        two.next = null;

        head = zeroList.next;
        return head;
    }

    /**
     * Given a singly linked list, group all odd nodes together followed by the even nodes.
     * Please note here we are talking about the node position and not the value in the nodes.
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
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     *
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

    /**
     * You are given a doubly linked list which in addition to the next and previous pointers,
     * it could have a child pointer, which may or may not point to a separate doubly linked list.
     * These child lists may have one or more children of their own, and so on, to produce a multilevel data structure.

     * Flatten the list so that all the nodes appear in a single-level, doubly linked list.
     * You are given the head of the first level of the list.
     *
     * @param head
     * @return
     */
    public Node flattenDoublyLinkedList(Node head) {
        ArrayDeque<Node> stack = new ArrayDeque<>();
        Node curr = head;
        while (curr != null) {
            if (curr.child != null) {
                if (curr.next != null)
                    stack.push(curr.next);
                curr.child.prev = curr;
                curr.next = curr.child;
                curr.child = null;
            }
            else {
                if (curr.next == null && !stack.isEmpty()) {
                    curr.next = stack.pop();
                    curr.next.prev = curr;
                }
            }
            curr = curr.next;
        }
        return head;
    }

    /**
     * Remove all elements from a linked list that have value val.
     * Input:  1->2->6->3->4->5->6, val = 6
     * Output: 1->2->3->4->5
     *
     * @param head
     * @param val
     * @return
     */
    public Node removeElements(Node head, int val) {
        Node curr = head, prev = new Node(0, head), newHead = prev;
        while (curr != null) {
            if (curr.data == val)
                prev.next = curr.next;
            else
                prev = prev.next;
            curr = curr.next;
        }
        return newHead.next;
    }

    /**
     * Given the head of a linked list, remove the nth node from the end of the list and return its head.
     * Input:  1->2->3->4->5->6, n = 2
     * Output: 1->2->3->4->6
     *
     * @param head
     * @param n
     * @return
     */
    public Node removeNthFromEnd(Node head, int n) {
        Node head1 = new Node(0);
        head1.next = head;
        Node fast = head1;
        Node slow = head1;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return head1.next;
    }
}

