package com.Algorithms;

import java.util.Arrays;
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

    static int shortestPathInABinaryMatrixUsingNodeClass(int[][] matrix, int[] source, int[] dest) {
        LinkedList<Node> queue = new LinkedList<>();

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        queue.add(new Node(source[0], source[1], 0));
        visited[source[0]][source[1]] = true;

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (!queue.isEmpty()) {
            Node node = queue.remove();

            if (node.row == dest[0] && node.col == dest[1])
                return node.dist;

            for (int[] d : directions) {
                int r = node.row + d[0];
                int c = node.col + d[1];

                if (r < 0 || r >= m || c < 0 || c >= n)
                    continue;

                if (matrix[r][c] == 1 && !visited[r][c]) {
                    queue.add(new Node(r, c, node.dist + 1));
                    visited[r][c] = true;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
//        int[][] mat = {{1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
//                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
//                {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
//                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
//                {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
//                {1, 1, 0, 0, 0, 0, 1, 0, 0, 1}};

//        System.out.println(shortestPathInABinaryMatrixUsingNodeClass(mat, new int[]{0, 0}, new int[]{3, 4}));

//        int[][] island = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
//        System.out.println(maxSizeOfIsland(island));
    }

    /**
     * Given a 2D Binary array, find the row with maximum number of 1s
     *
     * @param array
     * @return
     */
    public int findRowWithMaxOnes(int[][] array) {
        int row = 0, col = array[0].length - 1;  //Start from the top right corner
        int max1sRow = 0;

        while (row < array.length && col >= 0) {
            if (array[row][col] == 1) {
                max1sRow = row;
                col -= 1;  //Go left till you see 1
            } else {
                row += 1;  //Else go down
            }
        }

        return max1sRow;
    }

    /**
     * Given a binary matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
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
     * Given a grid, each cell is either empty (0) or blocked (1). Adjacent cells are connected 8-directionally.
     * Return the length of the shortest clear path from top-left to bottom-right.
     * If such a path does not exist, return -1.
     * Input:
     * [
     *    [0,0,0],
     *    [1,1,0],
     *    [1,1,0]
     * ]
     * Output: 4
     *
     * @param grid
     * @return
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] == 1)
            return -1;

        LinkedList<int[]> queue = new LinkedList<>();
        int rows = grid.length, cols = grid[0].length;

        int[][] distance = new int[rows][cols];  //We can use distance grid rather than boolean visited array.
        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }
        queue.add(new int[]{0, 0});
        distance[0][0] = 1;

        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

        while (!queue.isEmpty()) {
            int[] cellPosition = queue.remove();

            if (cellPosition[0] == rows - 1 && cellPosition[1] == cols - 1)
                return distance[rows - 1][cols - 1];

            for (int[] d : directions) {
                int r = cellPosition[0] + d[0];
                int c = cellPosition[1] + d[1];

                if (r < 0 || r >= rows || c < 0 || c >= cols)
                    continue;

                if (grid[r][c] == 0 && distance[r][c] == -1) {
                    queue.add(new int[]{r, c});
                    distance[r][c] = distance[cellPosition[0]][cellPosition[1]] + 1;
                }
            }
        }

        return -1;
    }

    /**
     * Given an m x n 2D binary grid which represents a map of '1's (land) and '0's (water),
     * return the number of islands.
     *
     * @param grid
     * @return
     */
    public int numberOfIslands(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == '1') {
                    dfs(grid, row, col);
                    count += 1;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[row].length) {
            return;
        }

        if (grid[row][col] == '0') {  //Base case
            return;
        }

        grid[row][col] = '0';

        dfs(grid, row - 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row + 1, col);
        dfs(grid, row, col + 1);

    }

    /**
     * Find the largest region of connected 1s in a given binary matrix.
     * (Without direction array).
     *
     * @param grid
     * @return
     */
    public int maxSizeOfIsland1(int[][] grid) {
        int maxSize = Integer.MIN_VALUE;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int size = dfs(grid, row, col);
                maxSize = Math.max(maxSize, size);
            }
        }
        return maxSize;
    }

    private int dfs(int[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            return 0;
        }
        if (grid[row][col] == 0) {
            return 0;
        }

        grid[row][col] = 0;

        return 1 + dfs(grid, row + 1, col) +
                dfs(grid, row, col + 1) +
                dfs(grid, row - 1, col) +
                dfs(grid, row, col - 1);
    }

    /**
     * Find the largest region of connected 1s in a given binary matrix.
     * (Use directions array).
     *
     * @param grid
     * @return
     */
    public int maxSizeOfIsland2(int[][] grid) {
        int maxsize = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                    int size = dfsUtil(grid, row, col, directions);
                    maxsize = Math.max(size, maxsize);
                }
            }
        }
        return maxsize;
    }

    private int dfsUtil(int[][] grid, int row, int col, int[][] directions) {
        grid[row][col] = 0;  //Mark this cell as visited
        int size = 1;
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (r >= 0 && c >= 0 && r < grid.length && c < grid[r].length && grid[r][c] == 1)
                size += dfsUtil(grid, r, c, directions);
        }
        return size;
    }

    /**
     * Given a 2D board containing 'X' and 'O' (the letter O), flip all 'O's to 'X's on the regions surrounded by 'X'.
     * You should ignore the boundaries (any 'O' on the border of the board must not be flipped to 'X').
     * Any 'O' that is connected to an 'O' on the border must not be flipped to 'X'.
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
            if (neiR >= 0 && neiR < board.length && neiC >= 0 &&
                    neiC < board[neiR].length && board[neiR][neiC] == 'O') {
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
        boolean[][] visited = new boolean[m][n];
        //We can’t use a HashSet<int[]> because the equality of new int[]{i,j} will differ.

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

                //From the old cell to the new cell position, add up the distance.
                matrix[r][c] = matrix[cellPosition[0]][cellPosition[1]] + 1;
                visited[r][c] = true;
                queue.add(newCellPosition);
            }
        }
        return matrix;
    }

    /**
     * Given a binary matrix, each cell has an initial state live (1) or dead (0).
     * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules:
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
