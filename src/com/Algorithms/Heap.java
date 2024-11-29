package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Heap {
    /**
     * Find k-th largest element in an array.
     * The array may contain duplicates.
     *
     * @param nums
     * @param k
     * @return
     */
    public int kthLargestElementUsingMinHeap(int[] nums, int k) {  // Runtime: O(n log k)
        // A min-heap allows us to keep the smallest of the top k elements at the root.
        // We insert elements into the heap. If the heap size exceeds k, we remove the smallest element (root).
        // This ensures that the root of the heap will be the kth largest element in the array.
        Queue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.remove();
            }
        }

        return minHeap.remove();
    }

    /**
     * Given an integer array nums and an integer k, return the k most frequent elements.
     * You may return the answer in any order.
     * nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequentElements(int[] nums, int k) {  // Runtime: O(n log n)
        // Use a hash map to count the frequency of each element in the array.
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // The comparator is used to make the heap a max-heap based on the frequency of elements.
        Queue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));
        maxHeap.addAll(map.keySet());

        int[] result = new int[k];

        // Extract the top k frequent elements from the heap
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.remove();
        }

        return result;
    }

    /**
     * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
     * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
     * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
     * nums1 = [1,7,11]
     * nums2 = [2,4,6]
     * k = 3
     * Output: [[1,2],[1,4],[1,6]]
     * The first 3 pairs are returned from the sequence:
     * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // The below declaration is equivalent to
        // new PriorityQueue<>((o1, o2) -> (o1.get(0) + o1.get(1)) - (o2.get(0) + o2.get(1)));
        Queue<List<Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> (o.get(0) + o.get(1))));

        for (int num1 : nums1) {
            for (int num2 : nums2) {
                minHeap.add(List.of(num1, num2));
            }
        }

        List<List<Integer>> list = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            list.add(minHeap.remove());
            if (minHeap.size() == 0)
                break;
        }

        return list;
    }

    /**
     * We have a list of points on the plane. Find the k closest points to the origin (0, 0).
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
     * Output: [[3,3],[-2,4]]
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosest(int[][] points, int k) {
        // Create a max heap based on the distance to origin
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) ->
                (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])
        );

        // Iterate through all points
        for (int[] point : points) {
            // Add the point to the heap
            maxHeap.offer(point);

            // If the heap size exceeds k, remove the farthest point
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // Prepare the result array
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }

        return result;
    }

    /**
     * Given an array and two integers k and x, return the k closest elements to x in the array.
     * An integer a is closer to x than an integer b if:

     * |a - x| < |b - x|, or
     * |a - x| == |b - x| and a < b
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> kClosestElements(int[] arr, int k, int x) {
        Queue<Integer> minHeap = new PriorityQueue<>((o1, o2) -> {
            if (Math.abs(o1 - x) == Math.abs(o2 - x)) {
                return o1 - o2;
            }
            return (Math.abs(o1 - x) - Math.abs(o2 - x));
        });

        for (int value : arr) {
            minHeap.add(value);
        }

        List<Integer> list = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            list.add(minHeap.remove());
        }

        return list;
    }

    /**
     * Running median in a data stream.
     * Solve using MinHeap and MaxHeap.
     * void addNum(int num) - Add a number from the data stream to the data structure.
     * double findMedian() - Return the median of all elements so far.
     *
     */
    static class RunningMedian {
        // A Min Heap to store the larger half of the numbers.
        Queue<Integer> minHeap = new PriorityQueue<>();

        // A Max Heap to store the smaller half of the numbers.
        Queue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        // By balancing these heaps, the median can be found efficiently.

        void addNum(int num) {  // Runtime: O(log n)
            if (minHeap.isEmpty() || num > minHeap.peek()) {
                minHeap.add(num);
            }
            else {
                maxHeap.add(num);
            }
            balanceHeaps();
        }

        private void balanceHeaps() {
            if (minHeap.size() - maxHeap.size() > 1) {
                maxHeap.add(minHeap.poll());
            } else if (maxHeap.size() - minHeap.size() > 1) {
                minHeap.add(maxHeap.poll());
            }
        }

        public double findMedian() {  // Runtime: O(1)
            if (minHeap.size() == maxHeap.size()) {
                // If even number of elements, median is the average of two middle elements
                return (double) (minHeap.element() + maxHeap.element()) / 2;  //element() works like peek()
            }
            // If odd, median is the top of the larger heap.
            // maxHeap always contains the smaller half of numbers,
            // including the median when the count is odd.
            // This is ensured during the balancing step.
            return (double) maxHeap.element();
        }
    }

    /**
     * You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].
     * Implement the SmallestInfiniteSet class.
     * SmallestInfiniteSet() initializes the SmallestInfiniteSet object to contain all positive integers.
     * int popSmallest() removes and returns the smallest integer contained in the infinite set.
     * void addBack(int num) adds a positive integer num back into the infinite set,
     * if it is not already in the infinite set.
     */
    static class SmallestInfiniteSet {
        int min;
        Queue<Integer> minHeap;

        public SmallestInfiniteSet() {
            min = 1;
            minHeap = new PriorityQueue<>();
        }

        public int popSmallest() {
            if (!minHeap.isEmpty()) {  //The minHeap is used only to store the added back elements.
                return minHeap.remove(); //If the minHeap has a value, it means the minimum element is here.
            }

            //The minHeap is empty. That means the minimum element is in the class variable "min".
            int tempMin = min;
            min += 1;  //Since we popped the minimum element, we need to increment the "min" variable to next element.
            return tempMin;
        }

        public void addBack(int num) {
            //We don't add back the minimum element if it is really not the minimum.
            if (num < min & !minHeap.contains(num)) {  //Heap allows duplicates. So we need to have this check.
                minHeap.add(num);
            }
        }
    }

    /**
     * Given a collection of stones weighing a non-negative number,
     * each turn, we choose the two heaviest stones and smash them together.
     * Suppose the stones have weights x and y, the result of this smash is
     * If x == y, both stones are totally destroyed.
     * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y - x.

     * stones = [2,7,4,1,8,1]
     * output = 1
     *
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        Queue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int stone : stones) {
            maxHeap.add(stone);
        }

        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.remove();
            int stone2 = maxHeap.remove();

            if (stone1 > stone2) {
                maxHeap.add(stone1 - stone2);
            }
        }
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    /**
     * Given a char array representing tasks CPU needs to do.
     * It contains capital letters A to Z where different letters represent different tasks.
     * Tasks could be done without original order. Each task could be done in one interval.
     * For each interval, CPU could finish one task or just be idle.
     * However, there is a cooling interval n between two same tasks,
     * there must be at least n intervals that CPU is doing different tasks or just be idle.
     * Return the intervals and task count by which the CPU will take to finish all the given tasks.
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * A -> B -> idle -> A -> B -> idle -> A -> B.
     *
     * @param tasks
     * @param k
     * @return
     */
    public int cpuTasks(char[] tasks, int k) {
        Map<Character, Integer> map = new HashMap<>();
        Queue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        for (char task : tasks) {
            map.put(task, map.getOrDefault(task, 0) + 1);  //task as key and its count as value
        }

        maxHeap.addAll(map.keySet());
        int count = 0;

        while (!maxHeap.isEmpty()) {
            //Store the removed characters and add it back after the completion of the task interval.
            List<Character> list = new ArrayList<>();
            for (int i = 0; i <= k; i++) {
                if (!maxHeap.isEmpty()) {
                    char mostOccurChar = maxHeap.poll();
                    map.put(mostOccurChar, map.getOrDefault(mostOccurChar, 1) - 1);
                    if (map.get(mostOccurChar) >= 1) {
                        list.add(mostOccurChar); //Add to the list only when this character's occurrence is at least 1.
                    }
                }
                count += 1;
                if (maxHeap.isEmpty() && list.isEmpty())
                    break;
            }
            //Add the removed characters back. We checked for the occurrences already inside the for loop.
            maxHeap.addAll(list);
        }
        return count;
    }

    /**
     * Given a string, sort it in decreasing order based on the frequency of characters.
     *
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        StringBuilder builder = new StringBuilder();
        Map<Character, Integer> map = new HashMap<>();
        Queue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        maxHeap.addAll(map.keySet());

        while (!maxHeap.isEmpty()) {
            char mostOccurChar = maxHeap.poll();
            while (map.get(mostOccurChar) >= 1) {
                builder.append(mostOccurChar);
                map.put(mostOccurChar, map.getOrDefault(mostOccurChar, 1) - 1);
            }
        }

        return builder.toString();
    }

    /**
     * Given a string, rearrange it such that the characters adjacent to each other are not the same.
     * input: "aabbcc"
     * output: "abcabc"
     *
     * @param s
     * @return
     */
    public String reorganizeString(String s) {
        // Count the frequencies of characters.
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            int count = map.getOrDefault(c, 0) + 1;
            map.put(c, count);

            if (count > (s.length() + 1) / 2) {
                // If the given string contains a character that occurs more than half of its length,
                // we cannot rearrange it.
                return "";
            }
        }

        // We are using Max Heap to always retrieve the character with the highest frequency,
        // ensuring that we place the most frequent character in the result first.
        Queue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));
        maxHeap.addAll(map.keySet());

        StringBuilder result = new StringBuilder();

        // Keep track of the last used character to avoid placing the same character consecutively.
        char prev = '#';

        while (!maxHeap.isEmpty()) {
            char current = maxHeap.poll();
            result.append(current);
            // Decrement the frequency in the map because we have added this character to the result.
            map.put(current, map.get(current) - 1);

            // Add the previously used character to the heap.
            // We should make sure that the prev character is only added back to the heap
            // when it is no longer in the heap after it has been used.
            // The previous character is nothing but the current character in the previous iteration.
            // We cannot add the current character in the same iteration
            // because this character might have more frequencies than the other characters.
            // So add it in the next iteration.
            if (prev != '#' && map.get(prev) > 0) {
                maxHeap.add(prev);
            }

            prev = current;
        }

        return result.toString();
    }
}
