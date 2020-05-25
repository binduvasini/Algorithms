package com.Algorithms;

import java.util.LinkedList;
import java.util.List;

public class Array2D {

    /**
     * Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
     * Return the intersection of these two interval lists.
     * A = [[0,2],[5,10],[13,23],[24,25]]
     * B = [[1,5],[8,12],[15,24],[25,26]]
     * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
     * @param A
     * @param B
     * @return
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int a = 0, b = 0;
        List<int[]> list = new LinkedList<>();

        while(a < A.length && b < B.length){
            int maxStartpoint = Math.max(A[a][0], B[b][0]);
            int minEndpoint = Math.min(A[a][1], B[b][1]);

            if(minEndpoint >= maxStartpoint){
                list.add(new int[]{maxStartpoint, minEndpoint});
            }

            if(minEndpoint == A[a][1])
                a += 1;
            else
                b += 1;
        }

        return list.toArray(new int[list.size()][]);
    }
}
