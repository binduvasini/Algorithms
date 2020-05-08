package com.Algorithms;

public class Matrix {
    void rotate(int[][] matrix) {
        //Transpose the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[i].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        //Swap the row values
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length - 1 - j];
                matrix[i][matrix.length - 1 - j] = temp;
            }
        }
    }

    /**
     * All possible paths from the first cell to last cell in a matrix.
     *
     * @param m
     * @param n
     * @return uniquePaths
     */
    int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
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
}
