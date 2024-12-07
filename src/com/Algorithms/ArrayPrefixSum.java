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
        // Use prefix sum technique.
        // Use a HashMap to calculate the cumulative sum as we iterate through the array.
        Map<Integer, Integer> map = new HashMap<>();  // map<prefixSum, frequency of this sum>
        map.put(0, 1);  // To handle the prefixSum 0.
        // When we keep adding the elements, we may get the prefixSum of exact target value.
        // When we subtract this prefixSum with target, we will get 0. So there needs to be a key 0.
        // For eg, nums = [1, 2, 1, 3], target = 3.

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {
            prefixSum += num;

            // At each step, check if the difference prefixSum - target exists in the HashMap
            if (map.containsKey(prefixSum - target)) {
                count += map.get(prefixSum - target);
            }

            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
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
        Map<Integer, Integer> map = new HashMap<>();  // map<prefixSum, index>
        map.put(0, -1);  // Edge case.

        int prefixSum = 0;
        int longest = 0;

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum - target)) {
                int prefixIndex = map.get(prefixSum - target);
                longest = Math.max(longest, i - prefixIndex);
            }
            // Track only the first occurrence of this cumulative sum.
            else {
                map.put(prefixSum, i);
            }
        }
        return longest;
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
        Map<Integer, Integer> map = new HashMap<>();  // map<remainder, index>
        map.put(0, -1);  // To handle the remainder 0

        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            int remainder = prefixSum % k;

            if (map.containsKey(remainder)) {
                int prefixIndex = map.get(remainder);
                if (i - prefixIndex >= 2) {  // As the question suggests, check if the subarray size is at least 2.
                    return true;
                }
            }
            // Track only the first occurrence of each remainder to maximize subarray length and avoid overwriting data.
            // This is why the map needs to be populated in the else block.
            else {
                // Store the remainder and index if not already in the map
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
    public int totalNumberOfSubarrayDivisibleByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();  // map<remainder, frequency of this remainder>
        map.put(0, 1);  // To handle the remainder 0

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {
            prefixSum += num;
            int remainder = prefixSum % k;

            remainder = (remainder < 0) ? remainder + k : remainder;
            // When the array contains negative elements.
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
    public int findMaxLengthOfEqual0And1(int[] nums) {
        // Transform this problem to finding the longest subarray with a cumulative sum of 0.
        // To achieve this, we add 1 for every 1 in the array, we subtract 1 for every 0 in the array.
        Map<Integer, Integer> map = new HashMap<>();  // map<prefixSum, index>
        map.put(0, -1);

        int prefixSum = 0;
        int longest = 0;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i] == 0 ? -1 : 1;  // Replace every 0 in the array with -1
            prefixSum += num;

            if (map.containsKey(prefixSum)) {
                int prefixIndex = map.get(prefixSum);
                longest = Math.max(longest, i - prefixIndex);
            }
            // Track only the first occurrence of each remainder to maximize subarray length and avoid overwriting data.
            // This is why the map needs to be populated in the else block.
            else {
                map.put(prefixSum, i);
            }
        }
        return longest;
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
        // Transform the problem into a numerical one by considering days with hours > 8 as +1 and others as -1.
        // Find the longest subarray where the cumulative prefix sum is +1.
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int prefixSum = 0;
        int longest = 0;
        for (int i = 0; i < hours.length; i++) {
            // Treat days with more than 8 hours of work as 1 (well-performing day),
            // and days with 8 or fewer hours as -1 (non-well-performing day).
            int num = hours[i] > 8 ? 1 : -1;

            prefixSum += num;

            // If the prefixSum is positive, it means we have more tiring days than non-tiring ones.
            // It means the entire interval from 0 to i is well-performing.
            if (prefixSum > 0) {  // Case 1: prefixSum is positive.
                longest = i + 1;
            }
            else {  // Case 2: prefixSum is negative.
                // Look for earlier positions where the cumulative sum was just slightly smaller than the current prefixSum.
                if (map.containsKey(prefixSum - 1)) {
                    // We are looking for a subarray where the count of well-performing days is
                    // greater than the count of non-well-performing days. So we're checking prefixSum - 1.
                    int prefixIndex = map.get(prefixSum - 1);
                    longest = Math.max(longest, i - prefixIndex);
                }
                map.putIfAbsent(prefixSum, i);
            }
        }
        return longest;
    }
}
