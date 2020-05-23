package com.Algorithms;

import java.util.LinkedList;
import java.util.List;

public class Array2D {

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
