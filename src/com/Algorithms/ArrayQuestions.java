package com.Algorithms;

import java.util.Arrays;
import java.util.HashMap;
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
     * Running median in a data stream.
     * Solve using MinHeap and MaxHeap.
     * <p>
     * void addNum(int num) - Add a integer number from the data stream to the data structure.
     * double findMedian() - Return the median of all elements so far.
     *
     * @param array
     * @return
     */

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

    void addNum(int num) {
        if (minHeap.size() == 0 || num > minHeap.peek())
            minHeap.add(num);
        else
            maxHeap.add(num);
        rebalanceHeaps();
    }

    double findMedian() {
        if (minHeap.size() == maxHeap.size()) {
            return (double) (minHeap.peek() + maxHeap.peek()) / 2;
        } else if (minHeap.size() > maxHeap.size()) {
            return (double) minHeap.peek();
        }
        return (double) maxHeap.peek();
    }

    private void rebalanceHeaps() {
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        } else if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.add(maxHeap.poll());
        }
    }

    /**
     * Given a collection of stones weighing a non-negative number, each turn, we choose the two heaviest stones and smash them together.
     * Suppose the stones have weights x and y, the result of this smash is
     * If x == y, both stones are totally destroyed.
     * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
     * <p>
     * Solve using max Heap.
     *
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int s : stones) {
            maxHeap.add(s);
        }
        while (!maxHeap.isEmpty() && maxHeap.size() > 1) {
            int x = maxHeap.remove();
            int y = maxHeap.remove();
            int z = 0;

            if (x > y)
                z = x - y;
            else if (x < y)
                z = y - x;
            if (z == 0)
                continue;
            maxHeap.add(z);
        }
        return (!maxHeap.isEmpty()) ? maxHeap.poll() : 0;
    }

    /**
     * Subarray with maximum sum. [2, -4, 1, 9, -6, 7, 3]. The subarray with maximum sum is 11: [1, 9, -6, 7].
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        return findMax(nums, 0, nums.length - 1);
    }

    private int findMax(int[] nums, int start, int end) {
        if (start == end)
            return nums[end];
        int mid = (start + end) / 2;
        int leftMax = findMax(nums, start, mid);
        int rightMax = findMax(nums, mid + 1, end);
        int crossingMax = crossingMax(nums, start, mid, end);
        return Math.max(crossingMax, Math.max(rightMax, leftMax));
    }

    private int crossingMax(int[] nums, int start, int mid, int end) {
        int left = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;
        int current = 0;
        for (int i = mid; i >= start; i--) {
            current += nums[i];
            left = Math.max(left, current);
        }
        current = 0;
        for (int i = mid + 1; i <= end; i++) {
            current += nums[i];
            right = Math.max(right, current);
        }
        return left + right;
    }

    /**Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
     * 
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        int sumSofar = 0;
        int longestSubarray = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (n == 0) {
                n = -1;
            }
            sumSofar += n;
            if (sumSofar == 0)
                longestSubarray = i + 1;
            if (map.containsKey(sumSofar)) {
                if (longestSubarray < i - map.get(sumSofar))
                    longestSubarray = i - map.get(sumSofar);
            } else
                map.put(sumSofar, i);
        }
        return longestSubarray;
    }
}
