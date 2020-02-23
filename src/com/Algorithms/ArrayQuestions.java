package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class ArrayQuestions {
    /**
     * Find k-th largest element in an array
     * Solve Using Min Heap
     */
    static int kthLargestElement(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int value : array) {
            minHeap.add(value);
            if (minHeap.size() > k)
                minHeap.remove();
        }
        return minHeap.remove();
    }

    static void mergeIntervals(int[][] intervals) {
        LinkedList<int[]> queue = new LinkedList<>();
        //sort the value in 0th index of each array
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        queue.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] currInterval = intervals[i];
            int[] prevInterval = queue.getLast();

            if (currInterval[0] > prevInterval[1]) { //if the current interval end point is already greater than the prev, we need it in the queue.
                queue.addLast(currInterval);
            }
            queue.getLast()[1] = Math.max(prevInterval[1], currInterval[1]); //update the interval in the queue rather the current one.
        }

        for (int[] inters : queue) {
            System.out.println(inters[0] + "," + inters[1]);
        }
    }

    boolean isDivideKConsecutivePossible(int[] nums, int k) {
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for (int num : nums) {  //Store the occurrences of elements in TreeMap
            tm.put(num, tm.getOrDefault(num, 0) + 1);
        }
        while (!tm.isEmpty()) {  //Scan the TreeMap
            int number = tm.firstKey();
            for (int i = 0; i < k; i++) {
                if (!tm.containsKey(number))
                    return false;
                if (tm.get(number) == 1)
                    tm.remove(number);
                else
                    tm.put(number, tm.get(number) - 1);
                number += 1;  //Next consecutive number
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] array = {{15, 18}, {1, 3}, {8, 10}, {2, 6}};
        mergeIntervals(array);
    }

    /**
     * Running median.
     * Solve using Min Heap.
     *
     * @param array
     * @return
     */
    double[] getMedians(int[] array) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        double[] medians = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            addNumberToHeap(array[i], minHeap, maxHeap); //Add the number to the right heap
            rebalanceHeaps(minHeap, maxHeap); //Compare the sizes of two heaps and keep them close to the same size as possible
            medians[i] = getMedianFromHeap(minHeap, maxHeap); //median so far
        }
        return medians;
    }

    private void addNumberToHeap(int number, PriorityQueue<Integer> minHeap, PriorityQueue<Integer> maxHeap) {
        if (minHeap.size() == 0 || number < minHeap.peek())
            minHeap.add(number);
        else
            maxHeap.add(number);
    }

    private void rebalanceHeaps(PriorityQueue<Integer> minHeap, PriorityQueue<Integer> maxHeap) {
        PriorityQueue<Integer> biggerSizeHeap = (minHeap.size() > maxHeap.size()) ? minHeap : maxHeap;
        PriorityQueue<Integer> smallerSizeHeap = (minHeap.size() > maxHeap.size()) ? maxHeap : minHeap;
        if ((biggerSizeHeap.size() - smallerSizeHeap.size()) > 1) {
            smallerSizeHeap.add(biggerSizeHeap.poll());
        }
    }

    private double getMedianFromHeap(PriorityQueue<Integer> minHeap, PriorityQueue<Integer> maxHeap) {
        PriorityQueue<Integer> biggerSizeHeap = (minHeap.size() > maxHeap.size()) ? minHeap : maxHeap;
        PriorityQueue<Integer> smallerSizeHeap = (minHeap.size() > maxHeap.size()) ? maxHeap : minHeap;
        if (biggerSizeHeap.size() == smallerSizeHeap.size())
            return (double) (biggerSizeHeap.peek() + smallerSizeHeap.peek()) / 2;
        else
            return biggerSizeHeap.peek();
    }
}
