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
    }
}
