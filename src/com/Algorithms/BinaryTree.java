package com.Algorithms;

import java.util.*;

public class BinaryTree {
    static class TreeNode {
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
        if (root == null) {
            return 0; // The height of a null node is 0
        }

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

    int maxDiameter = 0;

    int diameterOfBinaryTree(TreeNode root) {
        diameter(root);
        return maxDiameter;
    }

    private int diameter(TreeNode root) {
        if (root == null)
            return 0;

        int left = diameter(root.left);
        int right = diameter(root.right);

        // The diameter at the current node is left height + right height.
        maxDiameter = Math.max(maxDiameter, left + right);

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
        } else if (data < root.data) {  // The value is in the LST.
            // During the recursion, the value will match the root, so the predecessor will be set.
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
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes p and q.
     * The lowest common ancestor is defined as the deepest node that has both p and q as descendants
     * (where we allow a node to be a descendant of itself).
     * <p>
     * 3
     * / \
     * 5   1
     * / \ / \
     * 6  2 0  8
     * / \
     * 7   4
     * <p>
     * p = 5 and q = 1
     * In this case, the left subtree returns 5. Right subtree returns 1.
     * Both have returned non-null values. Therefore, the parent is the LCA.
     * <p>
     * p = 5 and q = 6
     * In this case, the left subtree returns 5. Right subtree returns null because 6 is not found in the right subtree.
     * When one of the subtrees returns a non-null value, that value itself is the LCA.
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    // Runtime: O(h), where h is the height of the tree.
    // In the case of a balanced tree, it would be O(log n).
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: If the root is null, there is no ancestor to return, so we return null.
        if (root == null) {
            return null;
        }

        // If both p and q are smaller than root, LCA must be in the left subtree.
        // Recursively search in the left subtree
        if (p.data < root.data && q.data < root.data) {
            return lowestCommonAncestor(root.left, p, q);
        }

        // If both p and q are greater than root, LCA must be in the right subtree.
        // Recursively search in the right subtree
        if (p.data > root.data && q.data > root.data) {
            return lowestCommonAncestor(root.right, p, q);
        }

        // If p and q are on different sides of the root, root is their LCA.
        // This also handles the case of root being either p or q. The node itself is the LCA.
        return root;
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
     * 4
     * /   \
     * 2     7
     * / \   / \
     * 1   3 6   9
     * <p>
     * Output:
     * 4
     * /   \
     * 7     2
     * / \   / \
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
    public TreeNode sortedListToBST(TreeNode head) {
        return buildTreePreOrder(head, null);
    }

    private TreeNode buildTreePreOrder(TreeNode head, TreeNode tail) {
        if (head == tail)
            return null;

        TreeNode mid = middleElem(head, tail);
        TreeNode node = new TreeNode(mid.data);

        node.left = buildTreePreOrder(head, mid);
        node.right = buildTreePreOrder(mid.next, tail);

        return node;
    }

    private TreeNode middleElem(TreeNode head, TreeNode tail) {
        //We need an argument for tail because we will pass the middle element to this method.
        TreeNode slow = head, fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Given the root of a binary tree, construct a string consisting of parenthesis and integers.
     * Input:
     * 1
     * /   \
     * 2     3
     * /
     * 4
     * Output: "1(2(4))(3)"
     * <p>
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
        if (node == null)
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
    public boolean isValidBST(TreeNode root) {  // Runtime: O(n) where n is the number of nodes
        // Start with the entire range of valid values for a BST, using Long.MIN_VALUE and Long.MAX_VALUE
        return checkBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean checkBST(TreeNode root, long min, long max) {
        // If the node is null, it is valid by definition (an empty tree or subtree is a valid BST).
        if (root == null) {
            return true;
        }
        // The current node's value must be strictly between 'min' and 'max'
        if (root.data <= min || root.data >= max) {
            return false;
        }
        // Recursively validate the left subtree, setting the max to the current node's value.
        // Recursively validate the right subtree, setting the min to the current node's value
        return checkBST(root.left, min, root.data) && checkBST(root.right, root.data, max);
    }

    /**
     * For a binary tree, we can define a flip operation as follows:
     * Choose any node, and swap the left and right child subtrees.
     * <p>
     * A binary tree X is flip equivalent to a binary tree Y if and only if
     * we can make X equal to Y after some number of flip operations.
     * <p>
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
     * Given the root of a binary search tree, and an integer k, return the k-th smallest element.
     *
     * @param root
     * @param k
     * @return
     */
    // Runtime: O(h+k), where h is the height of the tree. We traverse the BST until the kth node is processed.
    public int kthSmallest(TreeNode root, int k) {
        // Use a stack for in-order traversal.
        // In a Binary Search Tree (BST), the in-order traversal visits the nodes in ascending order.
        // Therefore, in-order traversal is perfect for finding the k-th smallest element.
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (root != null || !stack.isEmpty()) {
            // Traverse the left subtree.
            // Go to the left most node and reach the smallest element in the tree first.
            while (root != null) {
                stack.push(root);  // Keep adding the smaller nodes to the stack.
                root = root.left;
            }

            // Pop the top of the stack, which represents the next smallest element in ascending order.
            root = stack.pop();
            // Decrement k because we've processed one element
            k -= 1;
            // If we've found the kth smallest element, return it
            if (k == 0) {
                return root.data;
            }

            // After visiting a node, move to its right child and repeat the process.
            root = root.right;
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
        if (root == null)
            return null;
        levelOrderUtil(root);
        return root;
    }

    private void levelOrderUtil(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 1; i <= size; i++) {
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
    // Runtime: O(n) where n is the number of nodes
    int preIndex = 0;  // This needs to be a global variable instead of a local variable in the recursive method.

    // The same preIndex needs to be used across all recursive calls.
    // When we increment preIndex to move to the next node in the preorder array,
    // that change persists across all recursive calls.
    public TreeNode buildTreeFromPreorderAndInorder(int[] preorder, int[] inorder) {
        // Create a map to store the indices of elements in the inorder array
        Map<Integer, Integer> inOrderIndices = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inOrderIndices.put(inorder[i], i);
        }

        return buildRec(preorder, 0, inorder.length - 1, inOrderIndices);
    }

    private TreeNode buildRec(int[] preorder, int inStart, int inEnd, Map<Integer, Integer> inOrderIndices) {
        // Base case: if there are no elements in the current subtree
        if (inStart > inEnd) {
            return null;
        }

        // Create the root node with the current value in the preorder array.
        // The first element in preorder is the root node
        TreeNode root = new TreeNode(preorder[preIndex]);
        // Increment the preorder index for the next recursive call
        preIndex += 1;

        // Find the root node's index in inorder array
        int rootIndexInInorder = inOrderIndices.get(root.data);

        // Recursively build the left subtree and right subtree
        root.left = buildRec(preorder, inStart, rootIndexInInorder - 1, inOrderIndices);
        root.right = buildRec(preorder, rootIndexInInorder + 1, inEnd, inOrderIndices);

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
        Map<Integer, Integer> inOrderIndices = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inOrderIndices.put(inorder[i], i);
        }
        return build(postorder, 0, inorder.length - 1, inOrderIndices);
    }

    private TreeNode build(int[] postorder, int inStart, int inEnd, Map<Integer, Integer> inOrderIndices) {
        if (inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(postorder[postIndex]);
        postIndex -= 1;

        int rootInIndex = inOrderIndices.get(root.data);

        root.right = build(postorder, rootInIndex + 1, inEnd, inOrderIndices);
        root.left = build(postorder, inStart, rootInIndex - 1, inOrderIndices);
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
     * 1
     * / \
     * 2   2
     * / \ / \
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
     * 1
     * / \
     * 2   3
     * \   \
     * 4   5
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
     * @param targetSum
     * @return
     */

    public List<List<Integer>> pathSumRootToLeaf(TreeNode root, int targetSum) {
        // Do a DFS from root to all paths up to the leaves.
        // Add the current node and check the sum when we reach the leaf.
        // Backtrack.
        // Backtracking is efficient and doesn’t require a restart from the root,
        // as each recursive call manages the path to the current node independently.
        // Runtime: O(n) where n is the number of nodes in the tree.
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> currentPath = new LinkedList<>();
        findPathForSum(root, targetSum, currentPath, result);
        return result;
    }

    public void findPathForSum(TreeNode node, int targetSum, List<Integer> currentPath, List<List<Integer>> result) {
        if (node == null)  //Base case
            return;

        // Add the current node to the path
        currentPath.add(node.data);

        // Checking targetSum == 0 would be incorrect as it does not account for the value of the final leaf node in the path.
        // Therefore, we need to check whether the leaf node's value is equal to the targetSum
        // because the targetSum gets reduced in each recursion.
        if (root.left == null && root.right == null && root.data == targetSum) {
            result.add(new LinkedList<>(currentPath));
        }

        findPathForSum(node.left, targetSum - node.data, currentPath, result);
        findPathForSum(node.right, targetSum - node.data, currentPath, result);

        // Backtrack: remove the last element to explore other paths
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Given a binary tree and a sum, find the number of paths that sum to the given sum.
     * The path does not need to start or end at the root or a leaf,
     * but it must go downwards (traveling only from parent nodes to child nodes).
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        // Plain DFS considering each node as root and checking if the path contains the target sum.
        // Backtracking is not ideal here because it leads to redundant recalculations,
        // especially when paths can start from any node in the tree.

        // Runtime: O(N^2) in the worst case for an unbalanced tree,
        // because for each node, findPaths could traverse the rest of the tree.
        if (root == null) {
            return 0;
        }

        // Total paths starting from the current root
        int pathsFromRoot = countPath(root, targetSum);

        // Paths starting from left and right children
        int pathsOnLeft = pathSum(root.left, targetSum);
        int pathsOnRight = pathSum(root.right, targetSum);

        // Total paths is the sum of all three
        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    private int countPath(TreeNode node, int targetSum) {
        if (node == null) {
            return 0;
        }

        // Check if the current node completes the path with the target sum
        int count = (node.data == targetSum) ? 1 : 0;

        // Continue to find paths in the left and right subtrees with the updated sum
        count += countPath(node.left, targetSum - node.data);
        count += countPath(node.right, targetSum - node.data);

        return count;
    }

    /**
     * Given a binary tree, find the maximum path sum.
     * A path is defined as sequence of nodes from any node to any node in the tree along the parent-child connections.
     * The path must contain at least one node and does not need to go through the root.
     * The path can go up and down the tree, as long as it does not visit the same node more than once.
     * The path can start and end at any node in the tree.
     * 10
     * /  \
     * 2    10
     * / \     \
     * 20  1    -25
     * /  \
     * 3    4
     * The maximum path sum is 20 + 2 + 10 + 10 = 42.
     *
     * @param root
     * @return
     */
    int maxSum;

    public int maxPathSum(TreeNode root) {  // Runtime: O(n) where n is the number of nodes in the tree
        maxSum = Integer.MIN_VALUE;
        maxSum(root);
        return maxSum;
    }

    private int maxSum(TreeNode node) {
        // If the node is null, return 0 because a null node contributes nothing.
        if (node == null) {
            return 0;
        }

        // Store the maximum sum from the left and right subtrees, respectively.
        // Ignore negative paths. That's why we are comparing with 0.
        int leftMax = Math.max(0, maxSum(node.left));
        int rightMax = Math.max(0, maxSum(node.right));

        // Calculate the price of a new path through the current node.
        // Include the node itself, the best path from the left subtree, and the best path from the right subtree.
        // This is a potential candidate for the maximum sum.
        int newPathSum = node.data + leftMax + rightMax;

        // Update the overall maxSum
        maxSum = Math.max(maxSum, newPathSum);

        // Return the maximum gain if the current node is included in the path and one of its subtrees (left or right).
        return node.data + Math.max(leftMax, rightMax);
    }

    /**
     * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
     * An example is the root-to-leaf path 1->2->3 which represents the number 123.
     * Find the total sum of all root-to-leaf numbers.
     * Note: A leaf is a node with no children.
     * <p>
     * Input:
     * 4
     * / \
     * 9   0
     * / \
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
     * <p>
     * 1
     * /
     * 3
     * \
     * 2
     * <p>
     * Output: [3,1,null,null,2]
     * <p>
     * 3
     * /
     * 1
     * \
     * 2
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
        } else {
            modeCount = 1;
        }

        if (modeCount > maxCount) {
            maxCount = modeCount;
            result.clear();
            result.add(node.data);
        } else if (modeCount == maxCount) {
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

    private void reverseInOrder(TreeNode root) {
        if (root == null)
            return;

        reverseInOrder(root.right);
        sum += root.data;
        root.data = sum;
        reverseInOrder(root.left);
    }

    /**
     * Serialization: Convert a binary tree to a string in such a way that the structure and
     * values of the tree can be fully reconstructed.
     * Deserialization: Reconstruct the binary tree from the serialized string.
     */
    static class SerializeAndDeserialize {
        private final String NULL_STRING = "X";
        private final String DELIMITER = ",";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {  // Runtime: O(n) where n is the number of nodes
            // Use StringBuilder for efficient string concatenation
            StringBuilder sb = new StringBuilder();
            serializeHelper(root, sb);
            return sb.toString();
        }

        private void serializeHelper(TreeNode root, StringBuilder sb) {
            // Base case: if the current node is null, append "null" and return
            if (root == null) {
                sb.append(NULL_STRING).append(DELIMITER);
                return;
            }
            // Append the value of the current node followed by a delimiter
            sb.append(root.data).append(DELIMITER);

            // Recursively serialize the left and right subtrees
            serializeHelper(root.left, sb);
            serializeHelper(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {  // Runtime: O(n) where n is the number of nodes
            // Split the serialized string into an array of values using commas
            String[] nodes = data.split(DELIMITER);

            // Use a queue to process nodes in the correct order
            Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
            return deserializeHelper(queue);
        }

        private TreeNode deserializeHelper(Queue<String> queue) {
            // Retrieve the next value from the queue
            String val = queue.remove();

            // Base case: if the value is "null", return null (no node here)
            if (val.equals(NULL_STRING)) {
                return null;
            }
            // Create a new TreeNode with the current value
            TreeNode node = new TreeNode(Integer.parseInt(val));

            // Recursively reconstruct the left and right subtrees
            node.left = deserializeHelper(queue);
            node.right = deserializeHelper(queue);

            // Return the constructed node
            return node;
        }
    }
}