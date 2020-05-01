package com.Algorithms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        return Math.max(leftHeight, rightHeight) + 1;
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

    int fulldiameter = 0;

    int diameterOfBinaryTree(TreeNode root) {
        diameter(root);
        return fulldiameter;
    }

    private int diameter(TreeNode root) {
        if (root == null)
            return 0;
        int left = diameter(root.left);
        int right = diameter(root.right);
        fulldiameter = Math.max(fulldiameter, left + right);
        return Math.max(left, right) + 1;
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

    List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new LinkedList<>();
        List<Integer> rightSideList = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 1; i <= n; i++) {
                TreeNode treeNode = queue.poll();
                if (i == n && treeNode != null)
                    rightSideList.add(treeNode.data);
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
        }
        return rightSideList;
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

    HashMap<Integer, Integer> inOrderIndices = new HashMap<>();
    int preIndex = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        for (int i = 0; i < inorder.length; i++) {
            inOrderIndices.put(inorder[i], i);
        }

        return buildRec(preorder, inorder, 0, inorder.length - 1);
    }

    TreeNode buildRec(int[] preorder, int[] inorder, int inStart, int inEnd) {
        if (inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(preorder[preIndex]);
        preIndex += 1;

        int rootInIndex = inOrderIndices.get(root.data);

        root.left = buildRec(preorder, inorder, inStart, rootInIndex - 1);
        root.right = buildRec(preorder, inorder, rootInIndex + 1, inEnd);
        return root;
    }

    /**
     * Construct BST from a preorder traversal
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        return buildBST(preorder, 0, preorder.length - 1);
    }

    private TreeNode buildBST(int[] preorder, int start, int end) {
        if (start > end)
            return null;
        TreeNode root = new TreeNode(preorder[start]);
        int i = start;
        for (; i <= end; i++) {
            if (preorder[i] > preorder[start])
                break;
        }
        root.left = buildBST(preorder, start + 1, i - 1);
        root.right = buildBST(preorder, i, end);
        return root;
    }

    /**
     * Construct BST from a sorted array
     *
     * @param nums
     * @return
     */
    TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    TreeNode buildTree(int[] nums, int start, int end) {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums[mid]);

        node.left = buildTree(nums, start, mid - 1);
        node.right = buildTree(nums, mid + 1, end);

        return node;
    }


    /**
     * Construct BST from a sorted list
     */

    TreeNode sortedListToBST(Node head) {
        return buildTree(head, null);
    }

    private TreeNode buildTree(Node head, Node tail) {
        if (head == tail)
            return null;
        Node mid = middleElem(head, tail);
        TreeNode node = new TreeNode(mid.data);
        node.left = buildTree(head, mid);
        node.right = buildTree(mid.next, tail);
        return node;
    }

    private Node middleElem(Node head, Node tail) {  //We need an argument for tail because we will pass the middle element to this method.
        Node fast = head, slow = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Recover a BST in which two nodes are misplaced.
     */
    TreeNode swapFirst = null, swapSecond = null;
    TreeNode prev;

    public void recoverBST(TreeNode root) {
        prev = new TreeNode(Integer.MIN_VALUE);  //We can't use the root as prev. So assign a minimum value
        inOrderUtil(root);
        if (swapFirst != null && swapSecond != null) {
            int temp = swapFirst.data;  //Use the node's data to swap so that the tree will be updated.
            swapFirst.data = swapSecond.data;
            swapSecond.data = temp;
        }
    }

    void inOrderUtil(TreeNode root) {
        if (root == null)
            return;

        inOrderUtil(root.left);

        if (swapFirst == null && prev.data > root.data)
            swapFirst = prev;

        if (swapFirst != null && prev.data > root.data)
            swapSecond = root;

        prev = root;

        inOrderUtil(root.right);
    }

    /**
     * Invert a Binary Tree
     *
     * @param root
     * @return
     */
    TreeNode invertTree(TreeNode root) {
        preorderUtil(root);
        return root;
    }

    void preorderUtil(TreeNode root) {
        if (root == null)
            return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        preorderUtil(root.left);
        preorderUtil(root.right);
    }

    /**
     * Predecessor and successor in a BST
     */
    int predecessor, successor;

    void predSuc(TreeNode root, int val) {
        if (root == null)
            return;

        if (val == root.data) {  //The value is at the root.
            if (root.left != null) {  //LST is not null, so go to the right most element
                TreeNode node = root.left;
                while (node.right != null) {
                    node = node.right;
                }
                predecessor = node.data;
            }
            if (root.right != null) {  //RST is not null, so go to the left most element
                TreeNode node = root.right;
                while (node.left != null) {
                    node = node.left;
                }
                successor = node.data;
            }
        } else if (val < root.data) {  //The value is in the LST.
            successor = root.data;  //During the recursion, the value will match the root and it might not have a RST, so the successor might not be set. So, here we are setting it and calling the recursion.
            predSuc(root.left, val);
        } else {
            predecessor = root.data;  //During the recursion, the value will match the root and it might not have a LST, so the predecessor might not be set. So, here we are setting it and calling the recursion.
            predSuc(root.right, val);
        }
    }

    /**
     * Delete a node from a BST
     *
     * @param root
     * @param key
     * @return
     */
    TreeNode deleteNodeFromBST(TreeNode root, int key) {
        if (root == null)
            return null;

        if (key < root.data)  //Do your search in the LST
            root.left = deleteNodeFromBST(root.left, key);

        else if (key > root.data)  //Do your search in the RST
            root.right = deleteNodeFromBST(root.right, key);

        else {  //Found the node
            //Node with one child or no children
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            else {  //Node with two children
                root.data = findSuccessor(root);  //Copy the data from successor to this node.
                root.right = deleteNodeFromBST(root.right, root.data); //pass the RST of this node to recursion to delete the successor node.
            }
        }
        return root;
    }

    int findSuccessor(TreeNode root) {
        if (root.right != null) {
            TreeNode node = root.right;
            while (node.left != null) {
                node = node.left;
            }
            successor = node.data;
        }
        return successor;
    }

    /**
     * Check if a Binary Tree is symmetric.
     *
     * @param root
     * @return
     */
    boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
    }

    boolean isSymmetric(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;

        else if (root1 != null && root2 != null) {
            return (root1.data == root2.data &&
                    isSymmetric(root1.left, root2.right) &&
                    isSymmetric(root1.right, root2.left));
        }
        return false;
    }
}