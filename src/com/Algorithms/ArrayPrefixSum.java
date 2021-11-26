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
    public int subarraySum(int[] nums, int target) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();  //map<prefixSum, frequency of this sum>

        map.put(0, 1);
        //There may be an element in the array which is exactly the target.
        // For eg, target = 3, nums = [1, 2, 1, 3].
        // The last element 3 itself is a subarray.
        // When we are checking prefixSum - target, we should find a value for 0 in the hashmap (3 - 0).

        int prefixSum = 0;
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
     * return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k,
     * nums = [23,2,4,6,7], k = 6
     * Output: true
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean isSubarrayDivisibleByK(int[] nums, int k) {
        int prefixSum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();  //map<prefixSum, index>
        map.put(0, -1);  //To handle the remainder 0
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            int remainder = prefixSum % k;

            if (map.containsKey(remainder)) {
                int prefixIndex = map.get(remainder);
                if (i - prefixIndex > 1)  //As the question suggests, check if the subarray size is at least 2.
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
    public int subarrayDivisibleByK(int[] nums, int k) {
        int prefixSum = 0;
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
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
        int longestSubarrayLen = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum - target)) {
                int prefixIndex = map.get(prefixSum - target);
                longestSubarrayLen = Math.max(longestSubarrayLen, i - prefixIndex);
            } else {
                map.put(prefixSum, i);
            }
        }
        return longestSubarrayLen;
    }

    /**
     * Given a binary array, find the maximum length of a contiguous subarray with an equal number of 0 and 1.
     *
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        int prefixSum = 0;
        int longestSubarrayLen = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (n == 0) {
                n = -1;
            }
            prefixSum += n;
            if (prefixSum == 0)
                longestSubarrayLen = i + 1;
            if (map.containsKey(prefixSum)) {
                int prefixIndex = map.get(prefixSum);
                longestSubarrayLen = Math.max(longestSubarrayLen, i - prefixIndex);
            } else
                map.put(prefixSum, i);
        }
        return longestSubarrayLen;
    }


}
