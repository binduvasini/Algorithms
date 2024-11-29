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
        // Create a HashMap to store the numbers and their indices as we iterate through the array
        Map<Integer, Integer> map = new HashMap<>();  // map<element, index>

        // Loop through the array
        for (int i = 0; i < nums.length; i++) {
            // Calculate the complement (the number needed to reach the target)
            int complement = target - nums[i];

            // Check if the complement exists in the map
            if (map.containsKey(complement)) {
                // If found, return the indices of the current number and the complement
                return new int[]{map.get(complement), i};
            } else {
                // If not found, add the current number and its index to the map
                map.put(nums[i], i);
            }
        }

        // Return an empty array if no solution is found (this should not happen as per the problem constraint)
        return new int[]{};
    }

    /**
     * You are given a list of songs where the ith song has a duration of time[i] seconds.
     * Return the number of pairs of songs for which their total duration in seconds is divisible by 60.
     * Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.
     * Input: time = [30,20,150,100,40]
     * Output: 3
     * Explanation: Three pairs have a total duration divisible by 60:
     * (time[0] = 30, time[2] = 150): total duration 180
     * (time[1] = 20, time[3] = 100): total duration 120
     * (time[1] = 20, time[4] = 40): total duration 60
     *
     * @param time
     * @return
     */
    public int wholeMinuteDilemma(int[] time) {
        // Create a HashMap to store the frequency of remainders when divided by 60
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0; // Initialize a counter to count valid pairs

        // Loop through each time duration in the array
        for (int t : time) {
            // Calculate the remainder when the time is divided by 60
            int remainder = t % 60;

            // Calculate the complement such that (remainder + complement) is a multiple of 60
            int complement = (60 - remainder) % 60;

            // If the complement exists in the map, it means we can form valid pairs with previous times
            if (map.containsKey(complement)) {
                count += map.get(complement); // Add the frequency of the complement to the count
            }

            // Update the map with the current remainder, incrementing its frequency
            map.put(remainder, map.getOrDefault(remainder, 0) + 1);
        }

        // Return the total count of valid pairs
        return count;
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
                } else {
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
     * Subarray with maximum sum. nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4].
     * The subarray with maximum sum is 6: [4, -1, 2, 1].
     * Return the maximum sum.
     *
     * @param nums
     * @return
     */
    public int subarrayWithMaxSum(int[] nums) {
        // Use a running sum.
        int currentSum = 0;

        int maxSum = Integer.MIN_VALUE;

        for (int num : nums) {
            // Update currentSum:
            // - Either start a new subarray with 'num' (if currentSum + num is less than num)
            // - Or extend the current subarray by adding 'num' to currentSum
            currentSum = Math.max(num, currentSum + num);

            // Update maxSum to hold the maximum sum encountered so far
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
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
        for (int num : map.keySet()) {
            if (map.get(num) > nums.length / 2) {
                return num;
            }
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
        int[] merged = new int[leftArr.length + rightArr.length];
        int i = 0, lefti = 0, righti = 0;

        while (lefti < leftArr.length && righti < rightArr.length) {
            if (leftArr[lefti] <= rightArr[righti]) {
                merged[i] = leftArr[lefti];
                lefti += 1;
            } else {
                merged[i] = rightArr[righti];
                righti += 1;
            }
            i += 1;
        }

        //when one of the indices goes out of bounds, we copy the remaining elements from that array to the main array
        System.arraycopy(leftArr, lefti, merged, i, leftArr.length - lefti);
        System.arraycopy(rightArr, righti, merged, i, rightArr.length - righti);

        return merged;
    }

    /**
     * Best day to buy stock and the best day to sell it. Find the maximum profit given you buy and sell a stock once.
     *
     * @param prices
     * @return
     */
    public int maxProfitSellOnce(int[] prices) {
        // Keep track of the maximum profit found so far
        int maxProfit = 0;
        // Keep track of the least price encountered so far (initialized to a very high value)
        int leastPrice = Integer.MAX_VALUE;

        // Iterate through the array of prices
        for (int price : prices) {
            // Update the least price if the current price is lower
            leastPrice = Math.min(leastPrice, price);

            // Calculate the potential profit by selling at the current price
            // and update the maximum profit if this profit is higher
            maxProfit = Math.max(maxProfit, price - leastPrice);
        }

        // Return the maximum profit calculated
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

        // Loop through the array starting from the second day
        for (int i = 1; i < prices.length; i++) {
            // Check if the price on this day is higher than the price on the previous day
            if (prices[i] > prices[i - 1]) {
                // Add the profit from the transaction (difference between prices) to the total profit
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
     * Given an unsorted array of integers, find the length of the longest consecutive sequence of elements.
     * The question asks for the sequence. Not a subsequence. Therefore, the elements left and right are considered.
     * Input: [100, 4, 200, 1, 3, 2]
     * Output: 4
     * The longest consecutive sequence is [1, 2, 3, 4]. Therefore its length is 4.
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {  // Runtime: O(n).
        // Use a HashSet to process only unique numbers from the input array.
        // It also allows O(1) average time complexity for insertion and lookup.
        Set<Integer> set = new HashSet<>();

        // Add all elements to the HashSet.
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {
            // For each number in the set, we check if it is the start of a consecutive sequence.
            if (!set.contains(num - 1)) {
                int currentLongest = 1;  // This is the beginning of a sequence. So assign it to 1.
                int currentNum = num;  // Need a temp variable to increment this current number.

                // Check the next consecutive numbers until we can't find the next number in the sequence.
                while (set.contains(currentNum + 1)) {
                    currentLongest += 1;
                    currentNum += 1;
                }

                // After processing the current sequence, update the overall longest.
                longest = Math.max(longest, currentLongest);
            }
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
            left[i] = left[i - 1] * arr[i - 1];
        }

        right[arr.length - 1] = 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * arr[i + 1];
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

        for (int i = 0; i < n; i++) { // Start a loop to store the result.
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
        int minLength = Integer.MAX_VALUE;  //A marker to store the shortest size.

        while (end < nums.length) {  //We move the end pointer until we find the required sum.
            sum += nums[end];
            end += 1;

            while (sum >= target) {  //We found the sum greater or equal to target.
                // Now we shrink the window we find the required the shortest size.
                minLength = Math.min(minLength, end - start);
                sum -= nums[start];
                start += 1;
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
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

                if (sum == target) {  //Update the length only when the window sum is equal to target.
                    minSubarrayLen = Math.min(minSubarrayLen, end - start);
                }
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
     * rotate 1 step  to the right: [7,1,2,3,4,5,6]
     * rotate 2 steps to the right: [6,7,1,2,3,4,5]
     * rotate 3 steps to the right: [5,6,7,1,2,3,4]
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        // Normalize k:
        // If k is greater than the length of the array (n), rotating by k is equivalent to rotating by k % n
        // because rotating the array by its length results in the same array.
        k %= nums.length;

        // reverse the whole array to prepare it for rotation.
        reverse(nums, 0, nums.length - 1);
        // After reversing the entire array, reverse the first k elements to correctly position the
        // last k elements at the beginning of the array.
        reverse(nums, 0, k - 1);
        // Finally,
        // reverse the remaining n - k elements (which are now in the last positions) to restore the correct order.
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            // Swap elements at start and end
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            // Move towards the middle
            start += 1;
            end -= 1;
        }
    }

    /**
     * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
     * You have a car and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station.
     * You begin the journey with an empty tank at one of the gas stations.
     * Given two integer arrays gas and cost,
     * return the starting gas station's index if you can travel around the circuit once in the clockwise direction.
     * gas =  [1,2,3,4,5]
     * cost = [3,4,5,1,2]
     * Output: 3
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // At each gas station, you gain gas[i] amount of gas but spend cost[i] to travel to the next station.
        // If the total gas available across all stations is less than the total cost,
        // it's impossible to complete the circuit. This is why we calculate totalGas and totalCost.
        // Use totalGas and totalCost to verify if the journey is feasible at all.

        int totalGas = 0;
        int totalCost = 0;

        // Keep track of the current tank balance
        int tank = 0;

        // Variable to store the starting gas station index
        int start = 0;

        // Loop through all gas stations
        for (int i = 0; i < gas.length; i++) {
            // Add the current station's gas to the total gas
            totalGas += gas[i];

            // Add the current station's cost to the total cost
            totalCost += cost[i];

            // Update the current tank balance (gas[i] - cost[i])
            tank += gas[i] - cost[i];

            // If the tank balance becomes negative, it means we cannot reach the next station.
            // If tank is negative at station i,
            // it implies the sum of all stations between the current start and i is negative.
            // Hence, it can be a valid starting point.
            if (tank < 0) {
                // Reset the starting point to the next station
                start = i + 1;

                // Reset the tank to 0 because we are starting fresh
                tank = 0;
            }
        }

        // After looping, check if total gas is at least equal to total cost
        // If not, it's impossible to complete the circuit
        return totalGas >= totalCost ? start : -1;
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
        // Sliding window.
        int start = 0, end = 0;
        int zeroCount = 0, longest = 0;

        while (end < arr.length) {
            if (arr[end] == 0) {  //Expand the window
                zeroCount += 1;
            }

            if (zeroCount > k) {  //Shrink the window.
                if (arr[start] == 0) {  //Is the start element pointing at a zero?
                    // Decrement the count as we are shrinking the window.
                    zeroCount -= 1;
                }
                start += 1;
            }

            end += 1;
            longest = Math.max(longest, end - start);
        }
        return longest;
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

    /**
     * Given a list of cities, find the nearest cities that shares either an x or a y coordinate with the queried point.
     * The distance is denoted on a Euclidean plane: the difference in x plus the difference in y.
     * cities = ["p1","p2","p3"]
     * x = [30, 20, 10]
     * y = [30, 20, 30]
     * queries = ["p3", "p2", "p1"]
     * Output: ["p1", NONE, "p3"]
     *
     * @param cities
     * @param x
     * @param y
     * @param queries
     * @return
     */
    public List<String> closestCities(String[] cities, int[] x, int[] y, String[] queries) {
        Map<String, Integer> map = new HashMap<>(); // <city, index>
        for (int i = 0; i < cities.length; i++) {
            map.put(cities[i], i);
        }

        List<String> output = new ArrayList<>();
        for (String query : queries) {
            output.add(getClosestCity(cities, x, y, query, map));
        }
        return output;
    }

    private String getClosestCity(String[] cities, int[] x, int[] y, String queryCity, Map<String, Integer> map) {
        int ind = -1, minDist = Integer.MAX_VALUE;
        if (!map.containsKey(queryCity))
            return "NONE";

        int queryIndex = map.get(queryCity);
        int xCoord = x[queryIndex], yCoord = y[queryIndex];

        for (int i = 0; i < cities.length; i++) {
            if (i == queryIndex)
                continue;

            if (x[i] == xCoord || y[i] == yCoord) {
                int dist = Math.abs(x[i] - xCoord) + Math.abs(y[i] - yCoord);
                if (dist < minDist) {
                    minDist = dist;
                    ind = i;
                } else if (dist == minDist) {
                    if (cities[i].compareTo(cities[ind]) < 0) {
                        ind = i;
                    }
                }
            }
        }
        return ind == -1 ? "NONE" : cities[ind];
    }

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        int n = nums.length;
        List<String> output = new ArrayList<>();

        if (n == 0) {
            output.add(format(lower, upper));
            return output;
        }

        if (nums[0] > lower) {  //Handle lower bound
            output.add(format(lower, nums[0] - 1));
        }

        for (int i = 1; i < n; i++) {  //Handle the missing ranges in the rest of the elements in the array
            int prev = nums[i - 1], curr = nums[i];
            if (curr - prev > 1) {
                output.add(format(prev + 1, curr - 1));
            }
        }

        if (nums[n - 1] < upper) {  //Handle upper bound
            output.add(format(nums[n - 1] + 1, upper));
        }

        return output;
    }

    private String format(int a, int b) {
        return a == b ? String.valueOf(a) : a + "->" + b;
    }
}
