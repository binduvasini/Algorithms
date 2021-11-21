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

            if (currInterval[0] > prevInterval[1]) {
                //if the current interval end point is already greater than the prev, we need it in the queue.
                queue.addLast(currInterval);
            }
            queue.getLast()[1] = Math.max(prevInterval[1], currInterval[1]);
            //update the interval in the queue rather the current one.
        }

        for (int[] inters : queue) {
            System.out.println(inters[0] + "," + inters[1]);
        }
    }

    /**
     * Given an array of integers nums and a positive integer k,
     * find whether it's possible to divide this array into sets of k consecutive numbers.
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
     * Given a collection of stones weighing a non-negative number,
     * each turn, we choose the two heaviest stones and smash them together.
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
     * Subarray with maximum sum. nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4].
     * The subarray with maximum sum is 6: [4, -1, 2, 1].
     *
     * @param nums
     * @return
     */
    public int subarrayWithMaxSum(int[] nums) {
        return subArrayMaxSum(nums);
//        return findMax(nums, 0, nums.length - 1);
    }

    private int subArrayMaxSum(int[] nums) {
        int subarrayMax = nums[0], endOfCurrMax = nums[0];
        //Initializing with nums[0] rather than Integer.MIN_VALUE because the array may have all negative integers.
        for (int i = 1; i < nums.length; i++) {
            //When nums[i] is greater than endOfCurrMax + nums[i], a new subarray starts.
            endOfCurrMax = Math.max(endOfCurrMax + nums[i], nums[i]);
            //When endOfCurrMax is greater than subarrayMax, the current max subarray ends.
            subarrayMax = Math.max(subarrayMax, endOfCurrMax);

            //Use markers to mark the starting and ending positions of the subarray.
            //Use the below conditions rather than Math.max
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
     * Given a circular array, find the subarray with maximum sum.
     * A circular array means the end of the array connects to the beginning of the array.
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
     * Given an integer array, find one continuous subarray that if you only sort this subarray in ascending order,
     * the whole array will be sorted.
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
        //If k is greater than nums.length,
        // rotating the array (k % nums.length) times gives the same result as it is for k times.
        k %= nums.length;
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
                //Swap the numbers to keep in their positions.
                // The non-positive numbers will end up in the positions of positive missing numbers.
                swap(nums, nums[i] - 1, i);
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
     *
     * @param nums
     * @return
     */
    public int longestConsecutiveSequence(int[] nums) {
        int currentLongestConsec = 1, longestConsec = 0;
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums) {
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

    /**
     * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
     * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
     * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
     * Input: nums1 = [1,7,11],
     * nums2 = [2,4,6],
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
        if (nums1.length == 0 || nums2.length == 0)
            return new LinkedList<>();
        // The below declaration is equivalent to
        // new PriorityQueue<>((o1, o2) -> (o1.get(0)+o1.get(1)) - (o2.get(0)+o2.get(1)));
        PriorityQueue<List<Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> (o.get(0) + o.get(1))));
        for (int num1 : nums1) {
            for (int num2 : nums2) {
                minHeap.add(List.of(num1, num2));
                //We need to throw all the elements into minHeap.
                // So we aren't checking if the size > k and removing elements.
            }
        }
        List<List<Integer>> list = new LinkedList<>();
        while (k > 0) {
            list.add(minHeap.remove());
            if (minHeap.size() == 0)
                break;
            k -= 1;
        }
        return list;
    }

    /**
     * We have a list of points on the plane.  Find the k closest points to the origin (0, 0).
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
     * Output: [[3,3],[-2,4]]
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosestPoints(int[][] points, int k) {
        //Remember the comparator needs to return an int 0 or 1 or -1.
        //Directly subtracting two doubles and returning as it is will throw compile time error.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((o1, o2) -> {
            double o1Distance = Math.sqrt(o1[0] * o1[0] + o1[1] * o1[1]);
            double o2Distance = Math.sqrt(o2[0] * o2[0] + o2[1] * o2[1]);
            return Double.compare(o2Distance, o1Distance);
        });
        for (int[] point : points) {
            maxHeap.add(point);
            if (maxHeap.size() > k)
                maxHeap.remove();
        }
        List<int[]> list = new LinkedList<>();
        while (!maxHeap.isEmpty()) {
            list.add(maxHeap.remove());
        }
        return list.toArray(new int[list.size()][]);
    }

    /**
     * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
     * Input: [1,2,3,4,5]
     * Output: true
     * Input: [5,4,3,2,1]
     * Output: false
     *
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        int firstNum = Integer.MAX_VALUE, secondNum = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= firstNum)
                firstNum = num;
            else if (num <= secondNum)
                secondNum = num;
            else
                return true;
        }
        return false;
    }

    /**
     * Given an array nums of n integers where n > 1,
     * return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
     * In other words, find the product of all other elements in the array.
     * Input : [1, 2, 3, 4]
     * Output : [24, 12, 8, 6]
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        int[] output = new int[nums.length];

        left[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            left[i] = nums[i - 1] * left[i - 1];
        }

        right[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = nums[i + 1] * right[i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            output[i] = left[i] * right[i];
        }
        return output;
    }

    /**
     * There are 8 prison cells in a row, and each cell is either occupied or vacant.
     * Each day, whether the cell is occupied or vacant changes according to the following rules:
     * If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
     * Otherwise, it becomes vacant.
     * (Note that because the prison is a row, first and the last cells in the row can't have two adjacent neighbors.)
     * We describe the current state of the prison in the following way: cells[i] == 1 if the i-th cell is occupied,
     * else cells[i] == 0.
     * Given the initial state of the prison,
     * return the state of the prison after N days (and N such changes described above.)
     * <p>
     * Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
     * Output: [0,0,1,1,1,1,1,0]
     *
     * @param cells
     * @param N
     * @return
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        int[] nextDay = new int[cells.length];
        HashSet<String> set = new HashSet<>();
        boolean hasCycle = false;
        int cycle = 0;
        for (int i = 1; i <= N; i++) {
            for (int c = 1; c < cells.length - 1; c++) {
                nextDay[c] = (cells[c - 1] == cells[c + 1]) ? 1 : 0;  //Calculate the nextDay and store it in cells.
            }
            String cellString = Arrays.toString(nextDay);
            if (set.contains(cellString)) {
                hasCycle = true;
                break;
            }
            set.add(cellString);
            cycle += 1;
            cells = nextDay.clone();
        }
        if (hasCycle) {
            N = N % cycle;
            for (int i = 1; i <= N; i++) {
                for (int c = 1; c < cells.length - 1; c++) {
                    nextDay[c] = (cells[c - 1] == cells[c + 1]) ? 1 : 0;
                }
                cells = nextDay.clone();
            }
        }
        return cells;
    }

    /**
     * Write a program to find the n-th ugly number.
     * <p>
     * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
     * <p>
     * Example:
     * <p>
     * Input: n = 10
     * Output: 12
     * Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        TreeSet<Long> set = new TreeSet<>();
        set.add(1L);
        for (int i = 1; i < n; i++) {
            long first = set.pollFirst();
            set.add(first * 2);
            set.add(first * 3);
            set.add(first * 5);
        }

        return set.pollFirst().intValue();
    }

    /**
     * Best day to buy stock and the best day to sell it. Find the maximum profit given you buy and sell a stock once.
     *
     * @param prices
     * @return
     */
    public int maxProfitSellOnce(int[] prices) {
        if (prices.length == 0)
            return 0;
        int maxDiff = Integer.MIN_VALUE;
        int minimum = Integer.MAX_VALUE;
        for (int price : prices) {
            minimum = Math.min(minimum, price);
            maxDiff = Math.max(maxDiff, price - minimum);
        }
        return maxDiff;
    }

    /**
     * Best day to buy stock and the best day to sell it. You can only hold at most one share of the stock at any time.
     * However, you can buy it then immediately sell it on the same day.
     *
     * @param prices
     * @return
     */
    public int maxProfitSellMultipleTimes(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    /**
     * Find a contiguous subarray that has the largest product.
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int maxProd_currIndex = 0;
        int minProd_currIndex = 0;
        int maxProd = 0;

        if (nums.length == 1)
            return nums[0];

        for (int i : nums) {
            int tmp = maxProd_currIndex;

            maxProd_currIndex = Math.max(i, Math.max(i * tmp, i * minProd_currIndex));
            minProd_currIndex = Math.min(i, Math.min(i * tmp, i * minProd_currIndex));

            maxProd = Math.max(maxProd, maxProd_currIndex);
        }

        return maxProd;
    }

    /**
     * Given an integer array nums, return all the triplets that sum up to 0.
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> tripletSum(int[] nums, int target) {
        HashSet<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            HashSet<Integer> h = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                List<Integer> l = new ArrayList<>();
                if (h.contains(target - (nums[i] + nums[j]))) {
                    l.add(nums[i]);
                    l.add(nums[j]);
                    l.add(target - (nums[i] + nums[j]));
                } else {
                    h.add(nums[j]);
                }
                if (l.size() > 0) {
                    Collections.sort(l);
                    set.add(l);
                }
            }
        }

        return new ArrayList<>(set);
    }

    /**
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
     * n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0).
     * Find two lines, together with the x-axis forms a container, such that the container contains the most water.
     *
     * @param height
     * @return
     */
    public int containerWithMostWater(int[] height) {
        int i = 0, j = height.length - 1, maxarea = Integer.MIN_VALUE;
        while (i < j) {
            maxarea = Math.max(maxarea, (Math.min(height[i], height[j]) * (j - i)));
            if (height[i] < height[j]) i += 1;
            else j -= 1;
        }
        return maxarea;
    }
}
