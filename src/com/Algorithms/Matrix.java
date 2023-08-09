package com.Algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Matrix {

    /**
     * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
     * You have to rotate the image in-place.
     * Input:
     * [
     *    [1,2,3],
     *    [4,5,6],
     *    [7,8,9]
     * ]
     * Output:
     * [
     *    [7,4,1],
     *    [8,5,2],
     *    [9,6,3]
     * ]
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        //Transpose the matrix. Rows become columns and columns become rows.
        for (int r = 0; r < rows; r++) {
            for (int c = r; c < cols; c++) {
                int temp = matrix[r][c];
                matrix[r][c] = matrix[c][r];  //We are swapping the row value with column value.
                matrix[c][r] = temp;
            }
        }
        //Swap the row values
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols / 2; c++) {  //Traverse only till the first half of the column.
                int temp = matrix[r][c];
                matrix[r][c] = matrix[r][cols - 1 - c];
                matrix[r][cols - 1 - c] = temp;
            }
        }
    }

    /**
     * Given an n x n matrix where each of the rows and columns are sorted in ascending order,
     * find the kth smallest element in the matrix.
     * matrix =
     * [
     *   [ 1,  5,  9],
     *   [10, 11, 14],
     *   [12, 14, 15]
     * ],
     * k = 8,
     * return 14.
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallestElementInMatrix(int[][] matrix, int k) {
        Queue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int[] rows : matrix) {
            for (int value : rows) {
                maxHeap.add(value);
                if (maxHeap.size() > k)
                    maxHeap.remove();
            }
        }
        return maxHeap.remove();
    }

    /**
     * Given an integer target, return true if target is in matrix or false otherwise.
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0)
            return false;

        int row = 0;
        int col = matrix[0].length - 1;  //Start from the top right corner

        while (row < matrix.length && col >= 0) {
            if (target == matrix[row][col])
                return true;
            else if (target < matrix[row][col])
                col -= 1;  //Go left
            else
                row += 1;  //Go down
        }

        return false;
    }

    /**
     * Determine if a 9 x 9 Sudoku board is valid.
     * Only the filled cells need to be validated according to the following rules:
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        Set<String> set = new HashSet<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                char num = board[r][c];

                if (num != '.') {  //empty cell
                    if (
                            set.contains(num + " in row " + r) ||
                            set.contains(num + " in column " + c) ||
                            set.contains(num + " in block " + r / 3 + " " + c / 3)
                    ) {
                        return false;
                    }

                    set.add(num + " in row " + r);
                    set.add(num + " in column " + c);
                    set.add(num + " in block " + r / 3 + " " + c / 3);
                }
            }
        }
        return true;
    }

    /**
     * Given an m x n grid of characters board and a string word, return true if the word exists in the grid.
     * The word can be constructed from letters of sequentially adjacent cells,
     * where adjacent cells are horizontally or vertically neighboring.
     * The same letter cell may not be used more than once.
     *
     * @param board
     * @param word
     * @return
     */
    public boolean wordSearch(char[][] board, String word) {
        char[] wordArr = word.toCharArray();
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                if(board[r][c] == wordArr[0] && dfsUtil(board, wordArr, r, c, 0)){
                    //Just having an additional check here.
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsUtil(char[][] board, char[] wordArr, int r, int c, int wordIndex){
        if (wordIndex == wordArr.length) //Base case.
            return true;

        if (r < 0 || c < 0 || r >= board.length || c >= board[r].length || board[r][c] != wordArr[wordIndex]) {
            //border conditions
            return false;
        }

        char tmp = board[r][c];
        board[r][c] = '#'; //Marking the visited character.
        boolean exists = (dfsUtil(board, wordArr, r, c+1, wordIndex+1)
                || dfsUtil(board, wordArr, r, c-1, wordIndex+1)
                || dfsUtil(board, wordArr, r+1, c, wordIndex+1)
                || dfsUtil(board, wordArr, r-1, c, wordIndex+1));

        board[r][c] = tmp; //Setting the char back to original because
        // we might visit this char again in other cases (that are invoked from the for loop above)
        return exists;
    }

    /**
     * You are given an array coordinates,
     * coordinates[i] = [x, y], where [x, y] represents the coordinate of a point.
     * Check if these points make a straight line in the XY plane.
     *
     * @param coordinates
     * @return
     */
    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length <= 2)
            return true;

        int y1 = coordinates[0][1], y2 = coordinates[1][1];
        int num = y2 - y1;
        int x1 = coordinates[0][0], x2 = coordinates[1][0];
        int denom = x2 - x1;
        int gcdY2Y1 = gcd(num, denom);
        String slope = num / gcdY2Y1 + ":" + denom / gcdY2Y1;

        for (int i = 2; i < coordinates.length; i++) {
            int y3 = coordinates[i][1];
            int x3 = coordinates[i][0];
            int gcdY3Y1 = gcd(y3 - y1, x3 - x1);
            String slopeY3 = (y3 - y1) / gcdY3Y1 + ":" + (x3 - x1) / gcdY3Y1;
            if (!slopeY3.equals(slope))
                return false;
        }
        return true;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
     * Return the intersection of these two interval lists.
     * A = [[0,2],[5,10],[13,23],[24,25]]
     * B = [[1,5],[8,12],[15,24],[25,26]]
     * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
     *
     * @param A
     * @param B
     * @return
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int a = 0, b = 0;
        List<int[]> list = new LinkedList<>();

        while (a < A.length && b < B.length) {
            //The maximum start point from any two intervals and
            // the minimum end point from any two intervals must intersect.
            int maxStartpoint = Math.max(A[a][0], B[b][0]);
            int minEndpoint = Math.min(A[a][1], B[b][1]);

            if (minEndpoint >= maxStartpoint) {
                list.add(new int[]{maxStartpoint, minEndpoint});
            }

            if (minEndpoint == A[a][1])  //increment based on the minimum end point.
                a += 1;
            else
                b += 1;
        }

        return list.toArray(new int[list.size()][]);
    }


    /* Dynamic Programming */

    /**
     * Return the count of all possible paths from the first cell to the last cell in a matrix.
     * A robot is at the top-left corner of a m x n grid.
     * The robot can only move either down or right at any point in time.
     * The robot is trying to reach the bottom-right corner of the grid.
     * How many possible unique paths are there?
     *
     * @param rows
     * @param cols
     * @return uniquePaths
     */
    int uniquePaths(int rows, int cols) {
        int[][] dp = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            dp[r][0] = 1;
        }
        for (int c = 0; c < cols; c++) {
            dp[0][c] = 1;
        }
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                dp[r][c] = dp[r - 1][c] + dp[r][c - 1];
            }
        }
        return dp[rows - 1][cols - 1];
    }

    /**
     * Given a m x n grid filled with non-negative numbers,
     * find a path from top left to bottom right which minimizes the sum of all numbers along its path.
     * You can only move either down or right at any point in time.
     * Input:
     * [
     * [1,3,1],
     * [1,5,1],
     * [4,2,1]
     * ]
     * Output: 7
     * The path 1→3→1→1→1 minimizes the sum.
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows][cols];

        dp[0][0] = grid[0][0];

        for (int r = 1; r < rows; r++) {
            dp[r][0] = dp[r - 1][0] + grid[r][0];
        }
        for (int c = 1; c < cols; c++) {
            dp[0][c] = dp[0][c - 1] + grid[0][c];
        }
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                dp[r][c] = Math.min(dp[r - 1][c], dp[r][c - 1]) + grid[r][c];
            }
        }
        return dp[rows - 1][cols - 1];
    }

    /**
     * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
     * The dungeon consists of M x N rooms laid out in a 2D grid.
     * Our valiant knight (K) was initially positioned in the top-left room and
     * must fight his way through the dungeon to rescue the princess.
     * The knight has an initial health point represented by a positive integer.
     * If at any point his health point drops to 0, he can't proceed.
     * Some rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms;
     * other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).
     * The knight moves only rightward or downward in each step.
     * Determine the knight's minimum initial health so that he is able to rescue the princess.
     * Input:
     * [
     *    [-2 (K),	-3,	3],
     *    [-5,	   -10, 1],
     *    [10,	   30, -5 (P)]
     * ]
     * Output: 7.
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int rows = dungeon.length, cols = dungeon[0].length;
        int[][] dp = new int[rows + 1][cols + 1];

        for (int r = rows; r >= 0; r--) {
            dp[r][cols] = Integer.MAX_VALUE;
        }

        for (int c = cols; c >= 0; c--) {
            dp[rows][c] = Integer.MAX_VALUE;
        }

        dp[rows][cols - 1] = 1;
        dp[rows - 1][cols] = 1;

        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                dp[r][c] = Math.max(Math.min(dp[r + 1][c], dp[r][c + 1]) - dungeon[r][c], 1);
            }
        }

        return dp[0][0];
    }
}
