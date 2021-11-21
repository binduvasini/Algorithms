package com.Algorithms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

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

    private TreeNode addNodeSortedRec(TreeNode current, TreeNode newNode) {
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
        return current;
    }

    boolean contains(int value) {
        if (root == null) {
            return false;
        }
        return containsRec(root, value);
    }

    private boolean containsRec(TreeNode current, int value) {  //Use the same method for searching a value in a BST.
        if (current.data == value)
            return true;  //return current
        if (value < current.data) {
            if (current.left != null)
                return containsRec(current.left, value);
            else
                return false;  //return null
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

    List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new LinkedList<>();
        List<Integer> rightSideList = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
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

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer, List<Integer>> positions = new TreeMap<>();
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
     * Given preorder and inorder traversal of a tree, construct the binary tree.
     * preorder = [3,9,20,15,7]
     * inorder = [9,3,15,20,7]
     * @param preorder
     * @param inorder
     * @return
     */
    HashMap<Integer, Integer> inOrderIndices = new HashMap<>();
    int preIndex = 0;  //Initializing this inside the method throws ArraysIndexOutOfBoundsException during recursion.
    public TreeNode buildTreeFromPreorderAndInorder(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inOrderIndices.put(inorder[i], i);
        }
        return buildRec(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildRec(int[] preorder, int[] inorder, int inStart, int inEnd) {
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
     * Given inorder and postorder traversal of a tree, construct the binary tree.
     * inorder = [9,3,15,20,7]
     * postorder = [9,15,7,20,3]
     * @param inorder
     * @param postorder
     * @return
     */
//    HashMap<Integer, Integer> inOrderIndices = new HashMap<>();
    int postIndex = 0;
    public TreeNode buildTreeFromInorderAndPostorder(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;
        for(int i=0; i<inorder.length; i++){
            inOrderIndices.put(inorder[i], i);
        }
        return build(postorder, inorder, 0, inorder.length-1);
    }
    private TreeNode build(int[] postorder, int[] inorder, int inStart, int inEnd){
        if(inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(postorder[postIndex]);
        postIndex -= 1;

        int rootInIndex = inOrderIndices.get(root.data);

        root.right = build(postorder, inorder, rootInIndex+1, inEnd);
        root.left = build(postorder, inorder, inStart, rootInIndex-1);
        return root;
    }

    /**
     * Construct BST from a preorder traversal
     * Input: [8,5,1,7,10,12]
     * Output: [8,5,10,1,7,null,12]
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
     * Input: [-10,-3,0,5,9]
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
     * Construct BST from a sorted list.
     * Input: -10 -> -3 -> 0 -> 5 -> 9
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

    private Node middleElem(Node head, Node tail) {
        //We need an argument for tail because we will pass the middle element to this method.
        Node fast = head, slow = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Recover a BST in which two nodes are misplaced.
     * Input: [1,3,null,null,2]
     *
     *    1
     *   /
     *  3
     *   \
     *    2
     *
     * Output: [3,1,null,null,2]
     *
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
     * Invert a Binary Tree.
     * Input:
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9
     *
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
     * Predecessor and successor in a BST.
     */
    int predecessor, successor;

    void predSuc(TreeNode root, int data) {
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
            //During the recursion, the value will match the root,
            // and it might not have a RST, so the successor might not be set.
            // So, here we are setting it and calling the recursion.
            successor = root.data;
            predSuc(root.left, data);
        } else {
            //During the recursion, the value will match the root,
            // and it might not have a LST, so the predecessor might not be set.
            // So, here we are setting it and calling the recursion.
            predecessor = root.data;
            predSuc(root.right, data);
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
                //Copy the data from successor to this node.
                root.data = findSuccessor(root);
                //pass the RST of this node to recursion to delete the successor node.
                root.right = deleteNodeFromBST(root.right, root.data);
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
     * Given two binary trees, write a function to check if they are the same or not.
     * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
     * @param p
     * @param q
     * @return
     */
    public boolean isSame(TreeNode p, TreeNode q) {
        List<Integer> pList = preorder(p, new LinkedList<>());
        List<Integer> qList = preorder(q, new LinkedList<>());
        return pList.equals(qList);
    }

    private List<Integer> preorder(TreeNode node, LinkedList<Integer> list){
        if (node == null)
            list.add(-1);
        if (node != null) {
            list.add(node.data);
            preorder(node.left, list);
            preorder(node.right, list);
        }
        return list;
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
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean areCousins(TreeNode root, int x, int y) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean xExists = false, yExists = false;
            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.poll();
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
        sum -= root.data;
        if (sum == 0 && isLeaf(root))  //If the sum becomes 0, and if it is a leaf node, then return true
            return true;
        boolean left = hasPathSum(root.left, sum);
        boolean right = hasPathSum(root.right, sum);
        return left || right;  //Either of the subtrees has found the sum
    }

    boolean isLeaf(TreeNode root) {
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
        sum -= node.data;
        if (sum == 0 && isLeaf(node)) {  //If the sum becomes 0, and if it is a leaf node, then return true
            pathlist.add(new LinkedList(currentPath));
            currentPath.remove(currentPath.size() - 1);
            return;
        }
        findPathForSum(node.left, sum, currentPath);
        findPathForSum(node.right, sum, currentPath);
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

    public int pathSumParentToChild(TreeNode root, int sum) {
        if (root == null)
            return 0;
        countPath(root, sum);
        pathSumParentToChild(root.left, sum);
        pathSumParentToChild(root.right, sum);
        return count;
    }

    private void countPath(TreeNode node, int sum) {
        if (node == null)
            return;
        sum -= node.data;
        if (sum == 0)
            count += 1;
        countPath(node.left, sum);
        countPath(node.right, sum);
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
     *
     * An example is the root-to-leaf path 1->2->3 which represents the number 123.
     *
     * Find the total sum of all root-to-leaf numbers.
     *
     * Note: A leaf is a node with no children.
     *
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
     * Given a BST with duplicates, find all the mode(s) (the most frequently occurred element).
     * @param root
     * @return
     */
    int modeCount;
    int maxCount = 0;
    TreeNode prevNode = null;
    List<Integer> result = new LinkedList<>();

    public int[] findMode(TreeNode root) {
        traverseInOrder(root);
        int[] array = new int[result.size()];
        for(int i = 0; i < result.size(); i++) array[i] = result.get(i);
        return array;
    }

    private void traverseInOrder(TreeNode node) {
        if (node == null)
            return;

        traverseInOrder(node.left);


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

        prev = node;

        traverseInOrder(node.right);
    }
}