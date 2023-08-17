package com.Algorithms;

import java.util.*;

public class ArrayQuestions {

    /**
     * Given an unsorted array of integers nums and an integer target,
     * return the indices of the two numbers such that they add up to target.
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] output = new int[2];

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                output[0] = i;
                output[1] = map.get(target - nums[i]);
            }
            else {
                map.put(nums[i], i);
            }
        }

        return output;
    }

    /**
     * Given an integer array nums, return all the triplets that sum up to 0.
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> tripletSum(int[] nums) {
        Set<List<Integer>> resultSet = new HashSet<>();

        for (int i = 0; i < nums.length - 2; i++) {
            Set<Integer> set = new HashSet<>();

            for (int j = i + 1; j < nums.length; j++) {
                List<Integer> list = new ArrayList<>();

                if (set.contains(-(nums[i] + nums[j]))) {
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(-(nums[i] + nums[j]));
                }
                else {
                    set.add(nums[j]);
                }

                if (list.size() > 0) {  //If we don't have this check, an empty list gets added to the result.
                    Collections.sort(list);
                    resultSet.add(list);
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    /**
     * Given an array nums of size n, return the majority element.
     * The majority element is the element that appears more than ⌊n / 2⌋ times.
     * You may assume that the majority element always exists in the array.
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int k : map.keySet()){
            if (map.get(k) > nums.length / 2)
                return k;
        }
        return -1;
    }

    /**
     * Merge two sorted arrays.
     *
     * @param leftArr
     * @param rightArr
     * @return
     */
    public int[] mergeSortedArrays(int[] leftArr, int[] rightArr) {
        int[] tmp = new int[leftArr.length + rightArr.length];
        int i = 0, lefti = 0, righti = 0;

        while (lefti < leftArr.length && righti < rightArr.length) {
            if (leftArr[lefti] <= rightArr[righti]) {
                tmp[i] = leftArr[lefti];
                lefti += 1;
            } else {
                tmp[i] = rightArr[righti];
                righti += 1;
            }
            i += 1;
        }

        //when one of the indices goes out of bounds, we copy the remaining elements from that array to the main array
        System.arraycopy(leftArr, lefti, tmp, i, leftArr.length - lefti);
        System.arraycopy(rightArr, righti, tmp, i, rightArr.length - righti);

        return tmp;
    }

    /**
     * Given a collection of intervals, merge all overlapping intervals.
     * Input: [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     *
     * @param intervals
     */
    public int[][] mergeIntervals(int[][] intervals) {
        //sort the starting point of the intervals.
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        LinkedList<int[]> result = new LinkedList<>();

        result.addLast(intervals[0]);
        for (int[] current : intervals) {
            int[] prev = result.getLast();

            if (prev[1] >= current[0]) {  //The end point of the previous interval is greater than or equal to the
                // start point of the current interval. Therefore, it needs to be merged.
                result.getLast()[1] = Math.max(prev[1], current[1]);
            }
            else {
                result.addLast(current);
            }
        }

        return result.toArray(int[][]::new);
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

        int maxProfit = Integer.MIN_VALUE;
        int minimum = Integer.MAX_VALUE;

        for (int price : prices) {
            minimum = Math.min(minimum, price);
            maxProfit = Math.max(maxProfit, price - minimum);
        }

        return maxProfit;
    }

    /**
     * Best day to buy stock and the best day to sell it. You can only hold at most one share of the stock at any time.
     * However, you can buy it then immediately sell it on the same day. You should sell before you buy another stock.
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
     * Find a contiguous subarray that has the largest product. Return the product.
     * Input: nums = [-2,4,-1]
     * Output: 4
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int currentMax = 0, currentMin = 0;
        int maxProd = 0;

        if (nums.length == 1)
            return nums[0];

        for (int num : nums) {
            int tempMax = currentMax;  //Store the current max in temp variable
            // because we need it to find the current min

            currentMax = Math.max(num, Math.max(num * currentMin, num * currentMax));
            maxProd = Math.max(maxProd, currentMax);

            currentMin = Math.min(num, Math.min(num * currentMin, num * tempMax));
        }

        return maxProd;
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
        int currentLongest = 1, longest = 0;
        Set<Integer> set = new TreeSet<>();

        for (int num : nums) {  //Populate the TreeSet with all the elements
            set.add(num);
        }

        for (int num : set) {
            if (set.contains(num + 1)) {
                currentLongest += 1;
            }
            else {  //if the TreeSet DOES NOT contain its previous number, a new sequence begins.
                currentLongest = 1;
            }
            longest = Math.max(longest, currentLongest);
        }
        return longest;
    }

    /**
     * Given an unsorted array. Return whether an increasing subsequence of length 3 exists or not in the array.
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
     * @param arr
     * @return
     */
    public int[] productExceptSelf(int[] arr) {
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];
        int[] output = new int[arr.length];

        left[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            left[i] = arr[i - 1] * left[i - 1];
        }

        right[arr.length - 1] = 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            right[i] = arr[i + 1] * right[i + 1];
        }

        for (int i = 0; i < arr.length; i++) {
            output[i] = left[i] * right[i];
        }

        return output;
    }

    /**
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it can trap after raining.
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     *
     * @param arr
     * @return
     */
    public int trapRainWater(int[] arr) {
        if(arr.length == 0)
            return 0;
        int n = arr.length;

        //Maintain two arrays left and right
        int[] left = new int[n];
        int[] right = new int[n];
        int water = 0;

        left[0] = arr[0];
        for (int i = 1; i < n; i++) { //Left to right: Store the highest of previous and current elements
            left[i] = Math.max(left[i - 1], arr[i]);
        }

        right[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) { //Right to left: Store the highest of next and current elements
            right[i] = Math.max(right[i + 1], arr[i]);
        }

        for (int i = 0; i < n; i++) { //Scan the original array.
            // Subtract the minimum of left and right array’s elements with the current element
            water += Math.min(left[i], right[i]) - arr[i];
        }

        return water;
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
        int lo = 0, hi = height.length - 1;
        int maxArea = Integer.MIN_VALUE;

        while (lo < hi) {
            maxArea = Math.max(maxArea, (Math.min(height[lo], height[hi]) * (hi - lo)));
            if (height[lo] < height[hi])
                lo += 1;
            else
                hi -= 1;
        }

        return maxArea;
    }

    /**
     * Find the minimum length subarray whose sum is greater than or equal to the target.
     *
     * @param nums
     * @param target
     * @return
     */
    public int minSubArrayLen(int[] nums, int target) {
        int start = 0, end = 0;
        int sum = 0; //The sum at every window.
        int minSubarrayLen = Integer.MAX_VALUE;  //A marker to store the shortest size.

        while (end < nums.length) {  //We move the end pointer until we find the required sum.
            sum += nums[end];
            end += 1;

            while (sum >= target) {  //We found the sum greater or equal to target.
                // Now we move the start pointer until we find the required the shortest size.
                minSubarrayLen = Math.min(minSubarrayLen, end - start);
                sum -= nums[start];
                start += 1;
            }
        }
        return minSubarrayLen == Integer.MAX_VALUE ? 0 : minSubarrayLen;
    }

    /**
     * Find minimum length subarray whose sum is equal to the target.
     *
     * @param nums
     * @param target
     * @return
     */
    public int minSubarrayLen(int[] nums, int target) {
        int start = 0, end = 0;
        int sum = 0; //The sum at every window.
        int minSubarrayLen = Integer.MAX_VALUE;  //A marker to store the shortest size.

        while (end < nums.length) {  //We move the end pointer until we find the required sum.
            sum += nums[end];
            end += 1;

            while (sum > target) {  //We found the sum greater than target.
                // Now we move the start pointer until we find the required the shortest size.
                sum -= nums[start];
                start += 1;  //Shrink the window until the sum is equal to target.

                if (sum == target)  //Update the length only when the window sum is equal to target.
                    minSubarrayLen = Math.min(minSubarrayLen, end - start);
            }
        }
        return minSubarrayLen == Integer.MAX_VALUE ? 0 : minSubarrayLen;
    }

    /**
     * There are several cards arranged in a row, and each card has an associated number of points.
     * The points are given in the integer array cardPoints.
     * In one step, you can take one card from the beginning or from the end of the array. Take exactly k cards.
     * Your score is the sum of the points of the cards you have taken.
     * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.

     * cardPoints = [1,2,3,4,5,6,1], k = 3
     * Output: 12 (last three cards).
     *
     * @param cardPoints
     * @param k
     * @return
     */
    public int maxScore(int[] cardPoints, int k) {
        //Sliding window technique - need to think differently.
        //Use a sliding window of size length - k. And calculate the sum that is outside the window.
        int start = 0, end = cardPoints.length - k, sum = 0;

        for (int i = end; i < cardPoints.length; i++) {
            sum += cardPoints[i];
        }
        int result = sum;

        //Slide the window and check the sum outside the window.
        while (end < cardPoints.length) {
            sum = (sum - cardPoints[end]) + cardPoints[start];
            //Need to include the start element which is at the beginning of the array.
            // While doing this, remove the end element which is at the end of the array.
            result = Math.max(result, sum);

            start += 1;
            end += 1;
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
     * Given an integer array, find one continuous subarray that if you only sort this subarray in ascending order,
     * the whole array will be sorted.
     * Input: [2, 6, 4, 8, 10, 9, 15]
     * Output: 5
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        int start = 0, end = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

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
                start = i;
                break;
            }
        }

        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] < max) {
                end = i;
                break;
            }
        }

        return (start == 0 && end == 0) ? 0 : end - start + 1;
    }

    /**
     * Given an integer array nums, move all 0s to the end of it
     * while maintaining the relative order of the non-zero elements.
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int nonZeroInd = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[nonZeroInd] = num;
                nonZeroInd += 1;
            }
        }
        while (nonZeroInd < nums.length) {
            nums[nonZeroInd] = 0;
            nonZeroInd += 1;
        }
    }

    /**
     * Given an array of 0s and 1s, we may change up to K values from 0 to 1.
     * Return the length of the longest (contiguous) subarray that contains only 1s.
     *
     * @param arr
     * @param k
     * @return
     */
    public int longestOnesReplacingAtMostKZeros(int[] arr, int k) {
        int start = 0, end = 0;
        int zeroCount = 0, longestOnesLength = 0;

        while (end < arr.length) {
            if (arr[end] == 0)
                zeroCount += 1;

            if (zeroCount > k) {
                if (arr[start] == 0)
                    zeroCount -= 1;
                start += 1;
            }

            end += 1;
            longestOnesLength = Math.max(longestOnesLength, end - start);
        }
        return longestOnesLength;
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

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
     * Input: 5
     * Output:
     * [
     *       [1],
     *      [1,1],
     *     [1,2,1],
     *    [1,3,3,1],
     *   [1,4,6,4,1]
     * ]
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> pascalsTriangle(int numRows) {
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
}
