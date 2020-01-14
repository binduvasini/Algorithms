package com.Algorithms;

class StackNode {
    int data;
    StackNode next;

    public StackNode(int data) {
        this.data = data;
    }
}

public class Stack {
    StackNode top = null;
    int size = 0;

    public void push(int data) {
        size += 1;
        StackNode newNode = new StackNode(data);
        if (top == null) {
            top = newNode;
            return;
        }
        newNode.next = top;
        top = newNode;
    }

    public int pop() {
        size -= 1;
        if (top == null) {
            return 0;
        }
        int returndata = top.data;
        top = top.next;
        return returndata;
    }

    public int peek() {
        if (top == null) return 0;
        return top.data;
    }
}

