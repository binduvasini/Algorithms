package com.Algorithms;

class RopeNode {
    String data;
    int weight;
    RopeNode left;
    RopeNode right;

    public RopeNode(String data) {
        this.data = data;
        this.weight = data.length();
        this.left = null;
        this.right = null;
    }
}

public class Rope {

    RopeNode root;

    public Rope() {
        root = new RopeNode("");
    }

    public char charAtIndex(int index) {  //Assuming index starts at 1.
        return charAtIndex(root, index);
    }

    private char charAtIndex(RopeNode current, int i) {
        if (i > current.weight && current.right != null) {
            return charAtIndex(current.right, i - current.weight);
        }
        if (current.left != null) {  //Don't check if i < current.weight. It doesn't work.
            return charAtIndex(current.left, i);
        }
        return current.data.charAt(i - 1);  //charAt(i) will give array out of bounds. The index starts at 0.
    }

    public void concat(String str) {
        RopeNode newNode = new RopeNode(str);
        RopeNode newRoot = new RopeNode("");
        newRoot.left = root;
        newRoot.right = newNode;
        newRoot.weight = newRoot.left.weight;
        if (newRoot.left.right != null)
            newRoot.weight += newRoot.left.right.weight;  //Left subtree
        root = newRoot;
    }

    public static void main(String[] args) {  //Long string
        Rope r = new Rope();
        System.out.println("Rope Test\n");
        r.concat("hello ");
        r.concat("This is ");
        r.concat("a");
        r.concat(" test");
        System.out.println(r.charAtIndex(5));
    }
}
