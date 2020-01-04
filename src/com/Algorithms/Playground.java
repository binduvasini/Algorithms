package com.Algorithms;

public class Playground {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        binaryTree.addNodeSorted(1);
        binaryTree.addNodeSorted(2);
        binaryTree.addNodeSorted(5);
        binaryTree.addNodeSorted(6);
        binaryTree.addNodeSorted(4);
        binaryTree.addNodeSorted(3);


        binaryTree.levelOrder(binaryTree.root);


    }

}
