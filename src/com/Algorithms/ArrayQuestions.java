package com.Algorithms;

import java.util.PriorityQueue;

public class ArrayQuestions {
    static int kthLargestElement(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int value : array) {
            minHeap.add(value);
            if (minHeap.size() > k)
                minHeap.remove();
        }
        return minHeap.remove();
    }

    public static void main(String[] args) {
        int[] array = {3,2,3,1,2,4,9,5,5,6};
        System.out.println(kthLargestElement(array, 4));
    }
}
