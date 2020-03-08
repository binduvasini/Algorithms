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

}
