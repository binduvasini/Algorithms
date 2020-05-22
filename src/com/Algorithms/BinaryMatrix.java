package com.Algorithms;

import java.util.LinkedList;

public class BinaryMatrix {
    static class Node {
        int row;
        int col;
        int dist;

        public Node(int r, int c, int dist) {
            this.row = r;
            this.col = c;
            this.dist = dist;
        }
    }

    static int shortestPathInABinaryMatrix(int[][] matrix, int[] source, int[] dest) {
        LinkedList<Node> queue = new LinkedList<>();

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        queue.add(new Node(source[0], source[1], 0));
        visited[source[0]][source[1]] = true;

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (!queue.isEmpty()) {
            Node node = queue.remove();

            if (node.row == dest[0] && node.col == dest[1]) return node.dist;

            for (int[] d : directions) {
                int r = node.row + d[0];
                int c = node.col + d[1];

                if (r < 0 || r >= m || c < 0 || c >= n) continue;

                if (matrix[r][c] == 1 && !visited[r][c]) {
                    queue.add(new Node(r, c, node.dist + 1));
                    visited[r][c] = true;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] mat = {{1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 1, 0, 0, 1}};

        System.out.println(shortestPathInABinaryMatrix(mat, new int[]{0, 0}, new int[]{3, 4}));

        int[][] island = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        System.out.println(maxAreaOfIsland(island));
    }


    /**
     * Find the largest region of connected 1s in a given 2D binary array.
     *
     * @param grid
     * @return
     */
    static int maxAreaOfIsland(int[][] grid) {
        int maxsize = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    int size = DFSUtil(grid, row, col);
                    maxsize = Math.max(size, maxsize);
                }
            }
        }
        return maxsize;
    }

    private static int DFSUtil(int[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[r].length)
            return 0;
        if (grid[r][c] == 0)
            return 0;
        grid[r][c] = 0;
        return 1 +
                DFSUtil(grid, r - 1, c) +
                DFSUtil(grid, r, c - 1) +
                DFSUtil(grid, r + 1, c) +
                DFSUtil(grid, r, c + 1);
    }

    /**
     * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules:
     * <p>
     * 1) Any live cell with fewer than two live neighbors dies, as if caused by under-population.
     * 2) Any live cell with two or three live neighbors lives on to the next generation.
     * 3) Any live cell with more than three live neighbors dies, as if by over-population..
     * 4) Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     *
     * @param board
     */
    void gameOfLife(int[][] board) {

        int[][] dirs = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
        int m = board.length;
        int n = board[0].length;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {

                //For every cell, mark the number of neighbors alive.
                int neighborsAlive = 0;

                for (int[] dir : dirs) {
                    int neighR = row + dir[0];
                    int neighC = col + dir[1];

                    if (neighR < 0 || neighR >= m || neighC < 0 || neighC >= n)
                        continue;

                    //Math.abs will give us the original cell which was 1 but later turned into -1
                    if (Math.abs(board[neighR][neighC]) == 1)
                        neighborsAlive += 1;
                }

                //Rule 1 and 3:
                if (board[row][col] == 1 &&
                        (neighborsAlive < 2 || neighborsAlive > 3)) {
                    board[row][col] = -1;
                }

                //Rule 4:
                if (board[row][col] == 0 &&
                        neighborsAlive == 3) {
                    board[row][col] = 2;
                }
            }
        }
        //Update the board with actual values of 0 and 1.
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == -1)
                    board[row][col] = 0;
                else if (board[row][col] == 2)
                    board[row][col] = 1;
            }
        }
    }

    /**
     * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        boolean firstRow = false, firstCol = false;
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c] == 0) {
                    if (r == 0)
                        firstRow = true;
                    if (c == 0)
                        firstCol = true;
                    matrix[0][c] = 0;
                    matrix[r][0] = 0;
                }
            }
        }
        for (int r = 1; r < matrix.length; r++) {
            for (int c = 1; c < matrix[r].length; c++) {
                if (matrix[0][c] == 0 || matrix[r][0] == 0)
                    matrix[r][c] = 0;
            }
        }
        if (firstRow) {
            for (int c = 0; c < matrix[0].length; c++) {
                matrix[0][c] = 0;
            }
        }
        if (firstCol) {
            for (int r = 0; r < matrix.length; r++) {
                matrix[r][0] = 0;
            }
        }
    }

    /**
     * Given a 2D board containing 'X' and 'O' (the letter O), flip all 'O's to 'X's on the regions surrounded by 'X'.
     * You should ignore the boundaries (any 'O' on the border of the board must not be flipped to 'X'). Any 'O' that is connected to an 'O' on the border must not be flipped to 'X'.
     *
     * @param board
     */
    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public void surroundedRegions(char[][] board) {
        if (board.length == 0)
            return;
        int rows = board.length, cols = board[0].length;

        //Do a DFS on all the 4 boundaries.
        for (int c = 0; c < cols; c++) {
            if (board[0][c] == 'O')
                dfsUtil(board, 0, c);
        }

        for (int r = 0; r < rows; r++) {
            if (board[r][0] == 'O')
                dfsUtil(board, r, 0);
        }

        for (int c = cols - 1; c >= 0; c--) {
            if (board[rows - 1][c] == 'O')
                dfsUtil(board, rows - 1, c);
        }

        for (int r = rows - 1; r >= 0; r--) {
            if (board[r][cols - 1] == 'O')
                dfsUtil(board, r, cols - 1);
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 'O')
                    board[row][col] = 'X';
                else if (board[row][col] == '-')
                    board[row][col] = 'O';
            }
        }

    }

    private void dfsUtil(char[][] board, int r, int c) {
        board[r][c] = '-';
        for (int[] dir : dirs) {
            int neiR = r + dir[0];
            int neiC = c + dir[1];
            if (neiR >= 0 && neiR < board.length && neiC >= 0 && neiC < board[neiR].length && board[neiR][neiC] == 'O') {
                dfsUtil(board, neiR, neiC);
            }
        }
    }

    /**
     * Given a binary matrix, find the nearest 1 for each cell.
     *
     * @param matrix
     * @return
     */
    public int[][] findNearest1(int[][] matrix) {
        LinkedList<int[]> queue = new LinkedList<>();

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n]; //We can’t use a HashSet<int[]> because the equality of new int[]{i,j} will differ.


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        //BFS on this queue
        while (!queue.isEmpty()) {
            int[] cellPosition = queue.remove();

            for (int[] dir : directions) {
                int r = cellPosition[0] + dir[0];
                int c = cellPosition[1] + dir[1];
                int[] newCellPosition = new int[]{r, c};

                if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c])
                    continue;

                matrix[r][c] = matrix[cellPosition[0]][cellPosition[1]] + 1; //From the old cell to the new cell position, add up the distance.
                visited[r][c] = true;
                queue.add(newCellPosition);
            }
        }
        return matrix;
    }

    /* Dynamic Programming */

    /**
     * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     *Input:
     *
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if(matrix.length == 0)
            return 0;
        int[][] dp = new int[matrix.length+1][matrix[0].length+1];
        int rows = matrix.length, cols = matrix[0].length, maxSide = 0;

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if(matrix[r-1][c-1] == '1'){
                    dp[r][c] = Math.min(Math.min(dp[r][c-1], dp[r-1][c]), dp[r-1][c-1]) + 1;
                    maxSide = Math.max(maxSide, dp[r][c]);
                }
            }
        }

        return maxSide * maxSide;
    }

    /**
     * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
     *
     *
     *
     * Example 1:
     *
     * Input: matrix =
     * [
     *   [0,1,1,1],
     *   [1,1,1,1],
     *   [0,1,1,1]
     * ]
     * Output: 15
     * Explanation:
     * There are 10 squares of side 1.
     * There are 4 squares of side 2.
     * There is  1 square of side 3.
     * Total number of squares = 10 + 4 + 1 = 15.
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int[][] dp = new int[matrix.length+1][matrix[0].length+1];
        int rows = matrix.length, cols = matrix[0].length, count = 0;

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if(matrix[r-1][c-1] == 1){
                    dp[r][c] = Math.min(Math.min(dp[r][c-1], dp[r-1][c]), dp[r-1][c-1]) + 1;
                    count += dp[r][c];
                }
            }
        }

        return count;
    }

}
