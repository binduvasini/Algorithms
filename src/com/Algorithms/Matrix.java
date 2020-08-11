package com.Algorithms;

import java.util.LinkedList;

public class Matrix {
    void rotate(int[][] matrix) {
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
     * You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point. Check if these points make a straight line in the XY plane.
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


    /* Dynamic Programming */

    /**
     * Return the count of all possible paths from the first cell to the last cell in a matrix.
     * A robot is located at the top-left corner of a m x n grid. The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid.
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
     * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.
     * The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0, he can't proceed.
     * Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers). The knight moves only rightward or downward in each step.
     * Determine the knight's minimum initial health so that he is able to rescue the princess.
     * Input: [
     * [-2 (K),	-3,	3],
     * [-5,	-10, 1],
     * [10,	30,	-5 (P)]
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
