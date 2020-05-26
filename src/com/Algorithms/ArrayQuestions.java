package com.Algorithms;

import java.util.*;

public class ArrayQuestions {
    /**
     * Find k-th largest element in an array
     * Solve Using Min Heap
     */
    public int kthLargestElement(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int value : array) {
            minHeap.add(value);
            if (minHeap.size() > k)
                minHeap.remove();
        }
        return minHeap.remove();
    }

    /**
     * Given a collection of intervals, merge all overlapping intervals.
     * Input: [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     *
     * @param intervals
     */
    public void mergeIntervals(int[][] intervals) {
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

    /**
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into sets of k consecutive numbers.
     * Input: nums = [3,3,2,2,1,1], k = 3
     * Output: true
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean isDivideKConsecutivePossible(int[] nums, int k) {
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for (int num : nums) {  //Store the occurrences of elements in TreeMap
            tm.put(num, tm.getOrDefault(num, 0) + 1);
        }
        while (!tm.isEmpty()) {  //Scan the TreeMap
            int number = tm.firstKey();  //Start with the first Key every time.
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
     * Subarray with maximum sum. nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]. The subarray with maximum sum is 6: [4, -1, 2, 1].
     *
     * @param nums
     * @return
     */
    public int subarrayWithMaxSum(int[] nums) {
        return subArrayMaxSum(nums);
//        return findMax(nums, 0, nums.length - 1);
    }

    private int subArrayMaxSum(int[] nums) {
        int subarrayMax = nums[0], endOfCurrMax = nums[0];  //Initializing with nums[0] rather than Integer.MIN_VALUE because the array may have all negative integers.
        for (int i = 1; i < nums.length; i++) {
            endOfCurrMax = Math.max(endOfCurrMax + nums[i], nums[i]);  //When nums[i] is greater than endOfCurrMax + nums[i], a new subarray starts.
            subarrayMax = Math.max(subarrayMax, endOfCurrMax);  //When endOfCurrMax is greater than subarrayMax, the current max subarray ends.

            //Use markers to mark the starting and ending positions of the subarray. Use the below conditions rather than Math.max
            /*if (nums[i] > endOfCurrMax + nums[i]) {
                endOfCurrMax = nums[i];
                startInd = i;
            }
            if (endOfCurrMax > subarrayMax) {
                subarrayMax = endOfCurrMax;
                endInd = i;
            }*/
        }
        return subarrayMax;
    }

    // Another approach - divide and conquer.
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

    /**
     * Given a circular array, find the subarray with maximum sum. A circular array means the end of the array connects to the beginning of the array.
     * Input: [5,-3,5]
     * Output: 10
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        int subarrayMax = Integer.MIN_VALUE, endOfCurrMax = 0;
        int subarrayMin = Integer.MAX_VALUE, endOfCurrMin = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            endOfCurrMax = Math.max(endOfCurrMax + nums[i], nums[i]);
            subarrayMax = Math.max(subarrayMax, endOfCurrMax);

            endOfCurrMin = Math.min(endOfCurrMin + nums[i], nums[i]);
            subarrayMin = Math.min(subarrayMin, endOfCurrMin);

            sum += nums[i];
        }
        return subarrayMax > 0 ? Math.max(subarrayMax, sum - subarrayMin) : subarrayMax;
    }

    /**
     * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
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

    /**
     * Given an array, there is a sliding window of size k which is moving from left to right.
     * Return the max in each sliding window.
     * input [1,3,-1,-3,5,3,6,7], and k = 3
     * output [3,3,5,5,6,7]
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int start = 0, end = k - 1, ind = 0;
        int[] result = new int[nums.length - k + 1];
        while (end < nums.length) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
            for (int i = start; i <= end; i++) {
                maxHeap.add(nums[i]);
            }
            result[ind] = maxHeap.peek();
            start += 1;
            end += 1;
            ind += 1;
        }
        return result;
    }

    /**
     * Given an integer array, find one continuous subarray that if you only sort this subarray in ascending order, the whole array will be sorted.
     * Input: [2, 6, 4, 8, 10, 9, 15]
     * Output: 5
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        int startInd = 0, endInd = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                min = Math.min(min, nums[i]);
            }
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i + 1] < nums[i]) {
                max = Math.max(max, nums[i]);
            }
        }

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > min) {
                startInd = i;
                break;
            }
        }
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] < max) {
                endInd = i;
                break;
            }
        }

        return (startInd == 0 && endInd == 0) ? 0 : endInd - startInd + 1;
    }

    /**
     * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
     * Input: 5
     * Output:
     * [
     * [1],
     * [1,1],
     * [1,2,1],
     * [1,3,3,1],
     * [1,4,6,4,1]
     * ]
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generatePascalsTriangle(int numRows) {
        if (numRows == 0)
            return new LinkedList<>();
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> prev = new LinkedList<>(List.of(1));
        result.add(prev);
        for (int i = 1; i < numRows; i++) {
            List<Integer> list = new LinkedList<>();
            for (int j = 0; j <= i; j++) {  //We need a for loop here to loop through the elements in the prev array
                if (j == 0 || j == i)  //Left and right most elements are 1.
                    list.add(1);
                else
                    list.add(prev.get(j - 1) + prev.get(j));
            }
            result.add(list);
            prev = list;  //Save the prev array for the next run
        }
        return result;
    }

    /**
     * Given an array, rotate the array to the right by k steps.
     * Input: nums = [1,2,3,4,5,6,7], k = 3
     * Output: [5,6,7,1,2,3,4]
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        k %= nums.length;  //If k is greater than nums.length, rotating the array (k % nums.length) times gives the same result as it is for k times.
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        for (int i = start; i < end; i++, end--) {
            int tmp = nums[i];
            nums[i] = nums[end];
            nums[end] = tmp;
        }
    }

    /**
     * Given an unsorted integer array, find the smallest missing positive integer.
     * Input: [3,4,-1,1]
     * Output: 2
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0)
            return 1;
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);  //Swap the numbers to keep in their positions. The non-positive numbers will end up in the positions of positive missing numbers.
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums[nums.length - 1] + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * Find all the elements that do not appear in an array of [1, n] inclusive.
     * Input: [4,3,2,7,8,2,3,1]
     * Output: [5,6]
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                list.add(i + 1);
            }
        }
        return list;
    }

    /**
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
     * Input: [100, 4, 200, 1, 3, 2]
     * Output: 4
     * The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        int currentLongestConsec = 1, longestConsec = 0;
        TreeSet<Integer> set = new TreeSet<>();
        for(int num : nums){
            set.add(num);
        }
        for (int num : set) {
            if (set.contains(num + 1))
                currentLongestConsec += 1;
            else
                currentLongestConsec = 1;
            longestConsec = Math.max(longestConsec, currentLongestConsec);
        }
        return longestConsec;
    }
}
