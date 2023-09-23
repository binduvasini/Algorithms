package com.Algorithms;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    TreeNode next;  //This field is required for only one question.

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

    private boolean containsRec(TreeNode current, int value) {  //Use the same method for searching a value in a BST.
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
        if (root == null)
            return -1;
        int leftHeight = 1, rightHeight = 1;
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

    int fullDiameter = 0;

    int diameterOfBinaryTree(TreeNode root) {
        diameter(root);
        return fullDiameter;
    }

    private int diameter(TreeNode root) {
        if (root == null)
            return 0;
        int left = diameter(root.left);
        int right = diameter(root.right);
        fullDiameter = Math.max(fullDiameter, left + right);
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
        Queue<TreeNode> queue = new LinkedList<>();
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
        Deque<TreeNode> stack = new ArrayDeque<>();
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

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        Map<Integer, List<Integer>> positions = new TreeMap<>();
        Queue<TreeNode> qNodes = new LinkedList<>();
        Queue<Integer> qIndices = new LinkedList<>();

        qNodes.add(root);
        qIndices.add(0);
        positions.putIfAbsent(0, new LinkedList<>());
        positions.get(0).add(root.data);

        while (!qNodes.isEmpty()) {
            TreeNode treeNode = qNodes.remove();
            int nodeIndex = qIndices.remove();
            if (treeNode.left != null) {
                int leftPosition = nodeIndex - 1;
                positions.putIfAbsent(leftPosition, new LinkedList<>());
                positions.get(leftPosition).add(treeNode.left.data);
                qNodes.add(treeNode.left);
                qIndices.add(leftPosition);
            }
            if (treeNode.right != null) {
                int rightPosition = nodeIndex + 1;
                positions.putIfAbsent(rightPosition, new LinkedList<>());
                positions.get(rightPosition).add(treeNode.right.data);
                qNodes.add(treeNode.right);
                qIndices.add(rightPosition);
            }
        }
        return new LinkedList<>(positions.values());
    }

    /**
     * Predecessor and successor in a BST.
     */
    int predecessor, successor;

    public void predSuc(TreeNode root, int data) {
        if (root == null)
            return;

        if (data == root.data) {  //The value is at the root.
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
        } else if (data < root.data) {  //The value is in the LST.
            //During the recursion, the value will match the root, so the predecessor will be set.
            // this node might not have RST, so the successor might not be set.
            // So, here we are setting it first and then calling the recursion.
            successor = root.data;
            predSuc(root.left, data);
        } else {
            //During the recursion, the value will match the root,
            // and it might not have LST, so the predecessor might not be set.
            // So, here we are setting it and calling the recursion.
            predecessor = root.data;
            predSuc(root.right, data);
        }
    }

    /**
     * Given two binary trees, write a function to check if they are the same or not.
     * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.data == q.data) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
        return false;
    }

    /**
     * Invert a Binary Tree.
     * Input:
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9

     * Output:
     *      4
     *    /   \
     *   7     2
     *  / \   / \
     * 9   6 3   1
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        preorderUtil(root);
        return root;
    }

    private void preorderUtil(TreeNode root) {
        if (root == null)
            return;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        preorderUtil(root.left);
        preorderUtil(root.right);
    }

    /**
     * Construct BST from a sorted array
     * Input: [-10,-3,0,5,9]
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTreePreOrder(nums, 0, nums.length - 1);
    }

    private TreeNode buildTreePreOrder(int[] nums, int lo, int hi) {
        if (lo > hi)
            return null;

        int mid = (lo + hi) / 2;
        TreeNode node = new TreeNode(nums[mid]);

        node.left = buildTreePreOrder(nums, lo, mid - 1);
        node.right = buildTreePreOrder(nums, mid + 1, hi);

        return node;
    }

    /**
     * Construct BST from a sorted linked list.
     * Input: -10 -> -3 -> 0 -> 5 -> 9
     */
    public TreeNode sortedListToBST(Node head) {
        return buildTreePreOrder(head, null);
    }

    private TreeNode buildTreePreOrder(Node head, Node tail) {
        if (head == tail)
            return null;

        Node mid = middleElem(head, tail);
        TreeNode node = new TreeNode(mid.data);

        node.left = buildTreePreOrder(head, mid);
        node.right = buildTreePreOrder(mid.next, tail);

        return node;
    }

    private Node middleElem(Node head, Node tail) {
        //We need an argument for tail because we will pass the middle element to this method.
        Node slow = head, fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Given the root of a binary tree, construct a string consisting of parenthesis and integers.
     * Input:
     *      1
     *    /   \
     *   2     3
     *  /
     * 4
     * Output: "1(2(4))(3)"

     * Originally, it would have been "1(2(4)())(3()())", but omit all the unnecessary empty parenthesis pairs.
     * So the output will be "1(2(4))(3)"
     *
     * @param root
     * @return
     */
    public String tree2str(TreeNode root) {
        return preOrderUtil1(root);
    }

    private String preOrderUtil1(TreeNode node) {
        if(node == null)
            return "";
        if (node.left == null && node.right == null)
            return String.valueOf(node.data);
        if (node.left == null)
            return node.data + "()" + "(" + preOrderUtil1(node.right) + ")";
        if (node.right == null)
            return node.data + "(" + preOrderUtil1(node.left) + ")";
        return node.data + "(" + preOrderUtil1(node.left) + ")" + "(" + preOrderUtil1(node.right) + ")";
    }

    /**
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return checkBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean checkBST(TreeNode root, long min, long max) {
        if (root == null)
            return true;
        if (root.data <= min || root.data >= max)
            return false;
        return checkBST(root.right, root.data, max) && checkBST(root.left, min, root.data);
    }

    /**
     * For a binary tree, we can define a flip operation as follows:
     * Choose any node, and swap the left and right child subtrees.

     * A binary tree X is flip equivalent to a binary tree Y if and only if
     * we can make X equal to Y after some number of flip operations.

     * Given the roots of two binary trees root1 and root2,
     * return true if the two trees are flip equivalent or false otherwise.
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 == null || root2 == null || root1.data != root2.data) {
            return false;
        }
        return (
                flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)
                        ||
                        flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left)
        );
    }

    /**
     * Given the root of a binary search tree, and an integer k,
     * return the k-th smallest value (1-indexed) of all the values of the nodes in the tree.
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();

        while(!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            }
            else {
                k -= 1;
                root = stack.pop();
                if (k == 0)
                    return root.data;
                root = root.right;
            }
        }
        return -1;
    }

    /**
     * Given the root of a binary tree, print the nodes appearing on right side of the tree.
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new LinkedList<>();
        List<Integer> rightSideList = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();

            for (int i = 1; i <= n; i++) {
                TreeNode treeNode = queue.peek();
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

    public TreeNode connect(TreeNode root) {
        if(root == null)
            return null;
        levelOrderUtil(root);
        return root;
    }

    private void levelOrderUtil(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 1; i <= size; i++){
                TreeNode node = queue.peek();
                if (i == size)
                    node.next = null;
                else
                    node.next = queue.peek();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
    }

    /**
     * Given preorder and inorder traversal of a tree, construct the binary tree.
     * preorder = [3,9,20,15,7]
     * inorder = [9,3,15,20,7]
     *
     * @param preorder
     * @param inorder
     * @return
     */
    HashMap<Integer, Integer> inOrderIndices = new HashMap<>();
    int preIndex;  //Initializing this inside the method throws ArraysIndexOutOfBoundsException during recursion.
    public TreeNode buildTreeFromPreorderAndInorder(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inOrderIndices.put(inorder[i], i);
        }
        return buildRec(preorder, 0, inorder.length - 1);
    }

    private TreeNode buildRec(int[] preorder, int inStart, int inEnd) {
        if (inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(preorder[preIndex]);
        preIndex += 1;

        int rootInIndex = inOrderIndices.get(root.data);

        root.left = buildRec(preorder, inStart, rootInIndex - 1);
        root.right = buildRec(preorder, rootInIndex + 1, inEnd);
        return root;
    }

    /**
     * Given inorder and postorder traversal of a tree, construct the binary tree.
     * inorder = [9,3,15,20,7]
     * postorder = [9,15,7,20,3]
     *
     * @param inorder
     * @param postorder
     * @return
     */
//    HashMap<Integer, Integer> inOrderIndices = new HashMap<>();
    int postIndex;
    public TreeNode buildTreeFromInorderAndPostorder(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;
        for (int i = 0; i < inorder.length; i++){
            inOrderIndices.put(inorder[i], i);
        }
        return build(postorder, 0, inorder.length - 1);
    }
    private TreeNode build(int[] postorder, int inStart, int inEnd){
        if (inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(postorder[postIndex]);
        postIndex -= 1;

        int rootInIndex = inOrderIndices.get(root.data);

        root.right = build(postorder, rootInIndex + 1, inEnd);
        root.left = build(postorder, inStart, rootInIndex - 1);
        return root;
    }

    /**
     * Construct BST from a preorder traversal
     * Input: [8,5,1,7,10,12]
     * Output: [8,5,10,1,7,null,12]
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
        while (i <= end) {
            if (preorder[i] > preorder[start])
                break;
            i += 1;
        }
        root.left = buildBST(preorder, start + 1, i - 1);
        root.right = buildBST(preorder, i, end);
        return root;
    }

    /**
     * Delete a node from a BST
     *
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNodeFromBST(TreeNode root, int key) {
        if (root == null)
            return null;

        if (key < root.data)  //Do your search in the LST
            root.left = deleteNodeFromBST(root.left, key);

        else if (key > root.data)  //Do your search in the RST
            root.right = deleteNodeFromBST(root.right, key);

        else {  //Found the node with one child or no children
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            else {  //Node with two children
                //Copy the data from successor to this node.
                root.data = findSuccessor(root);
                //pass the RST of this node to recursion to delete the successor node.
                root.right = deleteNodeFromBST(root.right, root.data);
            }
        }
        return root;
    }

    private int findSuccessor(TreeNode root) {
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
     * Input:
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     * Output: true
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
    }

    private boolean isSymmetric(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;

        else if (root1 != null && root2 != null) {
            return (root1.data == root2.data &&
                    isSymmetric(root1.left, root2.right) &&
                    isSymmetric(root1.right, root2.left));
        }
        return false;
    }

    /**
     * Cousins in a Binary Tree
     * Input:
     *     1
     *    / \
     *   2   3
     *    \   \
     *     4   5
     * x = 4, y = 5.
     * Output: true
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean areCousins(TreeNode root, int x, int y) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean xExists = false, yExists = false;

            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.element();
                if (treeNode.data == x)
                    xExists = true;
                if (treeNode.data == y)
                    yExists = true;

                if (treeNode.left != null && treeNode.right != null) {
                    //Rather than checking if the children belong to a same parent,
                    // check if the parent's children are x & y.
                    if ((treeNode.left.data == x && treeNode.right.data == y)
                            ||
                            (treeNode.right.data == x && treeNode.left.data == y))
                        return false;
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
            if (xExists && yExists) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path
     * such that adding up all the values along the path equals the given sum.
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;

        if (sum == 0 && isLeaf(root))  //If the sum becomes 0, and if it is a leaf node, then return true
            return true;

        boolean left = hasPathSum(root.left, sum - root.data);
        boolean right = hasPathSum(root.right, sum - root.data);

        return left || right;  //Either of the subtrees has found the sum
    }

    private boolean isLeaf(TreeNode root) {
        return (root.left == null) && (root.right == null);
    }

    /**
     * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
     *
     * @param root
     * @param sum
     * @return
     */
    List<List<Integer>> pathlist = new LinkedList<>();

    public List<List<Integer>> pathSumRootToLeaf(TreeNode root, int sum) {
        List<Integer> currentPath = new LinkedList<>();
        findPathForSum(root, sum, currentPath);
        return pathlist;
    }

    public void findPathForSum(TreeNode node, int sum, List<Integer> currentPath) {
        if (node == null)
            return;

        currentPath.add(node.data);

        if (sum == 0 && isLeaf(node)) {  //If the sum becomes 0, and if it is a leaf node, then return
            pathlist.add(new LinkedList<>(currentPath));
            currentPath.remove(currentPath.size() - 1);
            return;
        }
        findPathForSum(node.left, sum - node.data, currentPath);
        findPathForSum(node.right, sum - node.data, currentPath);

        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Given a binary tree and a sum, find the number of paths that sum to the given sum.
     * The path does not need to start or end at the root or a leaf,
     * but it must go downwards (traveling only from parent nodes to child nodes).
     *
     * @param root
     * @param sum
     * @return
     */
    int count = 0;

    public void pathSumParentToChild(TreeNode root, int sum) {
        if (root == null)
            return;

        countPath(root, sum);

        pathSumParentToChild(root.left, sum);
        pathSumParentToChild(root.right, sum);

    }

    private void countPath(TreeNode node, int sum) {
        if (node == null)
            return;

        if (sum == 0)
            count += 1;

        countPath(node.left, sum - node.data);
        countPath(node.right, sum - node.data);
    }

    /**
     * Given a binary tree, find the maximum path sum.
     * A path is defined as sequence of nodes from any node to any node in the tree along the parent-child connections.
     * The path must contain at least one node and does not need to go through the root.
     *
     * @param root
     * @return
     */
    int maxVal;

    public int maxPathSum(TreeNode root) {
        maxVal = Integer.MIN_VALUE;
        maxSum(root);
        return maxVal;
    }

    private int maxSum(TreeNode root) {
        if (root == null)
            return 0;

        int leftmax = Math.max(0, maxSum(root.left));
        int rightmax = Math.max(0, maxSum(root.right));

        int currentmax = Math.max(root.data, Math.max(leftmax + root.data, rightmax + root.data));

        maxVal = Math.max(maxVal, leftmax + rightmax + root.data);

        return currentmax;
    }

    /**
     * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
     * An example is the root-to-leaf path 1->2->3 which represents the number 123.
     * Find the total sum of all root-to-leaf numbers.
     * Note: A leaf is a node with no children.

     * Input: [4,9,0,5,1]
     *     4
     *    / \
     *   9   0
     *  / \
     * 5   1
     * Output: 1026
     * Explanation:
     * The root-to-leaf path 4->9->5 represents the number 495.
     * The root-to-leaf path 4->9->1 represents the number 491.
     * The root-to-leaf path 4->0 represents the number 40.
     * Therefore, sum = 495 + 491 + 40 = 1026.
     *
     * @param root
     * @return
     */
    private int sum = 0;
    public int sumNumbers(TreeNode root) {
        sumRootToLeafNumbers(root, new StringBuilder());
        return sum;
//        return sumRootToLeafNumbers(root, 0);
    }

    //This is the solution I came up with. It requires declaring a sum class variable.
    private void sumRootToLeafNumbers(TreeNode root, StringBuilder builder) {
        if (root == null)
            return;
        builder.append(root.data);
        if (isLeaf(root)) {
            sum += Integer.parseInt(builder.toString());
            builder.setLength(builder.length() - 1);  //We need to remove the last inserted value here
            return;  //Return to avoid the IndexOutOfBoundsException at the last line.
        }
        sumRootToLeafNumbers(root.left, builder);
        sumRootToLeafNumbers(root.right, builder);
        builder.setLength(builder.length() - 1);  //We need to remove the last inserted value here as well.
    }

    //This sounds like a better solution.
    private int sumRootToLeafNumbers(TreeNode root, int currentSum) {
        if (root == null)
            return 0;
        currentSum = currentSum * 10 + root.data;
        if (isLeaf(root))
            return currentSum;
        //Here is where the new sum is calculated at every subtree.
        int leftSum = sumRootToLeafNumbers(root.left, currentSum);
        //Here is where the new sum is calculated at every subtree.
        int rightSum = sumRootToLeafNumbers(root.right, currentSum);
        return leftSum + rightSum;
    }

    /**
     * Given a binary tree, write a function to get the maximum width of the given tree.
     * The width of a tree is the maximum width among all levels.
     * The binary tree has the same structure as a full binary tree, but some nodes are null.
     * The width of one level is defined as the length between the end-nodes
     * (the leftmost and right most non-null nodes in the level),
     * where the null nodes between the end-nodes are also counted into the length calculation.
     * Input: [1,1,1,1,null,null,1,1,null,null,1]
     * Output: 8
     *
     * @param root
     * @return
     */
    public int maxWidthOfBinaryTree(TreeNode root) {
        int maxWidth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> indexQueue = new LinkedList<>();
        queue.add(root);
        indexQueue.add(1);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); //The queue size here is the number of nodes in this level
            int start = 0, end = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode treeNode = queue.remove();
                int index = indexQueue.remove();

                if (i == 0)
                    start = index;  //Record the left most node
                if (i == levelSize - 1)
                    end = index;  //Record the right most node

                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                    indexQueue.add(2 * index);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                    indexQueue.add(2 * index + 1);
                }
            }
            maxWidth = Math.max(maxWidth, end - start + 1);
        }
        return maxWidth;
    }


    /**
     * Recover a BST in which two nodes are misplaced.
     * Input: [1,3,null,null,2]

     *    1
     *   /
     *  3
     *   \
     *    2

     * Output: [3,1,null,null,2]

     *    3
     *   /
     *  1
     *   \
     *    2
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

    private void inOrderUtil(TreeNode root) {
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
     * Given a BST with duplicates, find all the mode(s) (the most frequently occurred element).
     *
     * @param root
     * @return
     */
    int modeCount;
    int maxCount = 0;
    TreeNode prevNode = null;
    List<Integer> result = new LinkedList<>();

    public int[] findMode(TreeNode root) {
        inOrderUtil1(root);
        int[] array = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            array[i] = result.get(i);
        }
        return array;
    }

    private void inOrderUtil1(TreeNode node) {
        if (node == null)
            return;

        inOrderUtil1(node.left);

        if (prevNode != null && prevNode.data == node.data) {
            modeCount += 1;
        }
        else {
            modeCount = 1;
        }

        if (modeCount > maxCount) {
            maxCount = modeCount;
            result.clear();
            result.add(node.data);
        }
        else if (modeCount == maxCount) {
            result.add(node.data);
        }

        prevNode = node;

        inOrderUtil1(node.right);
    }

    /**
     * Given the root of a binary tree, flatten the tree.
     *
     * @param root
     */
//    TreeNode prev = null;
    public void flatten(TreeNode root) {
        reversePreOrder(root);
    }

    public void reversePreOrder(TreeNode node) {
        if (node != null) {
            reversePreOrder(node.right);
            reversePreOrder(node.left);
            node.right = prev;
            node.left = null;
            prev = node;
        }
    }

    /**
     * Given the root of a Binary Search Tree (BST),
     * convert it to a Greater Tree such that every key of the original BST is changed to
     * the original key plus the sum of all keys greater than the original key in BST.
     *
     * @param root
     * @return
     */
//    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        reverseInOrder(root);
        return root;
    }

    private void reverseInOrder(TreeNode root){
        if(root == null)
            return;

        reverseInOrder(root.right);
        sum += root.data;
        root.data = sum;
        reverseInOrder(root.left);
    }
}

class SerializeAndDeserialize {
    private final String NULL_STRING = "X";
    private final String DELIMITER = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        createStringPreOrder(root, sb);
        return sb.toString();
    }

    private void createStringPreOrder(TreeNode root, StringBuilder sb){
        if (root == null) {
            sb.append(NULL_STRING).append(DELIMITER);
        }
        else {
            sb.append(root.data).append(DELIMITER);
            createStringPreOrder(root.left, sb);
            createStringPreOrder(root.right, sb);
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(DELIMITER)));
        return createTreePreOrder(queue);
    }

    private TreeNode createTreePreOrder(Queue<String> queue){
        String val = queue.remove();
        if (val.equals(NULL_STRING)) {
            return null;
        }
        else {
            TreeNode node = new TreeNode(Integer.parseInt(val));
            node.left = createTreePreOrder(queue);
            node.right = createTreePreOrder(queue);
            return node;
        }
    }
}