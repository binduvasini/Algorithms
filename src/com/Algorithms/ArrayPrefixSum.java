package com.Algorithms;

import java.util.HashMap;
import java.util.Map;

public class ArrayPrefixSum {
    /**
     * Given an array of integers nums and a target,
     * find the total number of continuous subarray whose sum equals to target.
     *
     * @param nums
     * @param target
     * @return
     */
    public int totalNumberOfSubarrayWhoseSumEqualsTarget(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();  //map<prefixSum, frequency of this sum>

        map.put(0, 1);  //To handle the prefixSum 0.
        // When we keep adding the elements, we may get the prefixSum of exact target value.
        // When we subtract this prefixSum with target, we will get 0. So there needs to be a key 0.
        // For eg, nums = [1, 2, 1, 3], target = 3.

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {
            prefixSum += num;

            if (map.containsKey(prefixSum - target)) {
                count += map.get(prefixSum - target);
            }

            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }

    /**
     * Given an array nums and an integer k,
     * return true if nums has a continuous subarray of size at least 2
     * whose elements sum up to a value which is a multiple of k.
     * nums = [23,2,4,6,7], k = 6
     * Output: true
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean isSubarrayDivisibleByK(int[] nums, int k) {
        int prefixSum = 0;
        Map<Integer, Integer> map = new HashMap<>();  //map<remainder, index>
        map.put(0, -1);  //To handle the remainder 0

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            int remainder = prefixSum % k;

            if (map.containsKey(remainder)) {
                int prefixIndex = map.get(remainder);
                if (i - prefixIndex >= 2)  //As the question suggests, check if the subarray size is at least 2.
                    return true;
            } else {
                map.put(remainder, i);
            }
        }
        return false;
    }

    /**
     * Given an array nums and an integer k, return the total number of subarray that have a sum divisible by k.
     * nums = [4,5,0,-2,-3,1], k = 5
     * Output: 7
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarrayCountDivisibleByK(int[] nums, int k) {
        int prefixSum = 0;
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();  //map<remainder, frequency of this remainder>
        map.put(0, 1);  //To handle the remainder 0

        for (int num : nums) {
            prefixSum += num;
            int remainder = prefixSum % k;

            remainder = (remainder < 0) ? remainder + k : remainder;
            //When the array contains negative elements.
            // Dividing negative will yield a negative remainder. To fix that, add k to make it positive.
            // All the remainders will repeat as a cycle up to a number less than k.

            if (map.containsKey(remainder)) {
                count += map.get(remainder);
            }
            map.put(remainder, map.getOrDefault(remainder, 0) + 1);
        }
        return count;
    }

    /**
     * Given a binary array, find the maximum length of a contiguous subarray with an equal number of 0 and 1.
     * nums = [1, 0, 0, 1, 0]
     * output: 4
     *
     * @param nums
     * @return
     */
    public int findMaxLength0And1(int[] nums) {
        // We have transformed this problem to finding the longest subarray with a cumulative sum of 0.
        // To achieve this, we add 1 for every 1 in the array, we subtract 1 for every 0 in the array.
        int prefixSum = 0;
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();  //map<prefixSum, index>
        map.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i] == 0 ? -1 : 1;  //Replace every 0 in the array with -1
            prefixSum += num;

            if (map.containsKey(prefixSum)) {
                int prefixIndex = map.get(prefixSum);
                maxLen = Math.max(maxLen, i - prefixIndex);
            } else {
                map.put(prefixSum, i);
            }
        }
        return maxLen;
    }

    /**
     * Find the length of the longest well-performing interval in a given array of work hours.
     * A well-performing interval is defined as a subarray where the number of days with more than 8 hours of work
     * exceeds the number of days with 8 or fewer hours.
     * Input: hours = [9,9,6,0,6,6,9]
     * Output: 3
     *
     * @param hours
     * @return
     */
    public int longestWPI(int[] hours) {
        //This is similar to the above problem. We transform this problem to finding the cumulative sum.
        int prefixSum = 0;
        int maxLen = 0;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        for (int i = 0; i < hours.length; i++) {
            // Treat days with more than 8 hours of work as 1 (tiring days),
            // and days with 8 or fewer hours as -1 (non-tiring days).
            int num = hours[i] > 8 ? 1 : -1;

            prefixSum += num;

            if (prefixSum > 0) {  // If the prefixSum is positive, it means we have more tiring days than non-tiring ones.
                maxLen = i + 1;
            }
            else {
                if (map.containsKey(prefixSum - 1)) {  // Check if we've seen this prefixSum before
                    // We are looking for a subarray where the count of tiring days is
                    // greater than the count of non-tiring days. So we're checking prefixSum - 1.
                    int prefixIndex = map.get(prefixSum - 1);
                    maxLen = Math.max(maxLen, i - prefixIndex);
                }
                map.putIfAbsent(prefixSum, i);  //We are populating the map with prefixSum.
                // So do it without the else condition.
            }
        }
        return maxLen;
    }
    /**
     * Find the maximum length subarray for a given sum.
     * nums = { 10, 5, 2, 7, 1, 9 },
     * target = 15
     * Output: 4
     *
     * @param nums
     * @param target
     * @return
     */
    public int maxLengthSubarray(int[] nums, int target) {
        int prefixSum = 0;
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();  //map<prefixSum, index>

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum - target)) {
                int prefixIndex = map.get(prefixSum - target);
                maxLen = Math.max(maxLen, i - prefixIndex);
            } else {
                map.put(prefixSum, i);
            }
        }
        return maxLen;
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
        int maxSum = Integer.MIN_VALUE, prefixSum = 0;
        for (int num : nums) {
            prefixSum += num;
            maxSum = Math.max(maxSum, prefixSum);

            if (prefixSum < 0) {
                prefixSum = 0;
            }
        }

        return maxSum;
    }

    /**
     * A circular array means the end of the array connects to the beginning of the array.
     * Given a circular array, find the subarray with maximum sum. Return the maximum sum.
     * Input: [5,-3,5]
     * Output: 10
     *
     * @param nums
     * @return
     */
    public int circularSubarrayWithMaxSum(int[] nums) {
        int currentMax = 0, currentMin = 0;
        int minSum = Integer.MAX_VALUE;
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;

        for (int num : nums) {
            currentMax = Math.max(currentMax + num, num);
            maxSum = Math.max(maxSum, currentMax);

            currentMin = Math.min(currentMin + num, num);
            minSum = Math.min(minSum, currentMin);

            sum += num;
        }

        return maxSum > 0 ? Math.max(maxSum, sum - minSum) : maxSum;  //Total sum - minimum subarray sum in the middle.
    }
}
