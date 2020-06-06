package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Array2D {

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
            //The maximum start point from any two intervals and the minimum end point from any two intervals must intersect.
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

    /**
     * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.
     * <p>
     * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
     * <p>
     * Example:
     * <p>
     * matrix = [
     * [ 1,  5,  9],
     * [10, 11, 14],
     * [12, 14, 15]
     * ],
     * k = 8,
     * <p>
     * return 14.
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int[] cells : matrix) {
            for (int cell : cells) {
                maxHeap.add(cell);
                if (maxHeap.size() > k)
                    maxHeap.remove();
            }
        }
        return maxHeap.remove();
    }

    /**
     * There are 2N people a company is planning to interview. The cost of flying the i-th person to city A is costs[i][0], and the cost of flying the i-th person to city B is costs[i][1].
     * <p>
     * Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.
     * <p>
     * Input: [[10,20],[30,200],[400,50],[30,20]]
     * Output: 110
     * Explanation:
     * The first person goes to city A for a cost of 10.
     * The second person goes to city A for a cost of 30.
     * The third person goes to city B for a cost of 50.
     * The fourth person goes to city B for a cost of 20.
     * <p>
     * The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.
     *
     * @param costs
     * @return
     */
    public int twoCityScheduling(int[][] costs) {
        int N = costs.length / 2, total = 0;
        int A = 0, B = 0;
        //We need to sort the array in decreasing order because we need the minimum cost. So rule out the maximum costs first.
        //If we sort it in increasing order instead, we will get the maximum cost.
        Arrays.sort(costs, (c1, c2) -> Math.abs(c2[0] - c2[1]) - Math.abs(c1[0] - c1[1]));

        for (int[] cost : costs) {
            if ((cost[0] <= cost[1] && A < N) || B == N) {  //making sure we have N candidates in each city.
                A += 1;
                total += cost[0];
            } else {
                B += 1;
                total += cost[1];
            }
        }
        return total;
    }
}
