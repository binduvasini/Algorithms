package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;

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
}
