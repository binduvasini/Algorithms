package com.Algorithms;

import java.util.Arrays;

public class BinarySearch {

    public int binarySearch(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1; // Initialize search range

        while (lo <= hi) { // Continue as long as there is a valid range
            int mid = lo + (hi - lo) / 2; // Compute the midpoint (to avoid overflow)

            if (target == nums[mid]) {
                return mid; // Target found
            } else if (target < nums[mid]) {
                hi = mid - 1; // Search the left half
            } else {
                lo = mid + 1; // Search the right half
            }
        }

        return -1; // Target not found
    }

    /**
     * Search an element in a rotated sorted array.
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchRotatedSortedArrayWithoutDuplicates(int[] nums, int target) {  //Runtime: O(log n)
        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            // Check if the middle element is the target
            if (target == nums[mid]) {
                return mid;
            }

            // Check if the left side of the array (from left to mid) is sorted
            if (nums[lo] <= nums[mid]) { // the left side is sorted for sure.

                // Now we need to search for the target here.
                if (target < nums[mid] && target >= nums[lo]) {
                    hi = mid - 1;  // Narrow the search to the left half
                }
                else {
                    lo = mid + 1;  // Otherwise, search the right half
                }
            } else { // the right side (mid to high) is sorted for sure.
                if (target > nums[mid] && target <= nums[hi]) {
                    lo = mid + 1;
                }
                else {
                    hi = mid - 1;
                }
            }
        }

        // check the final remaining element
        return target == nums[lo] ? lo : -1;
    }

    /**
     * Find the minimum element in a rotated sorted array.
     * nums = [12, 14, 1, 5, 8, 9]
     * Output: 1
     *
     * @param nums
     * @return
     */
    public int findMinRotatedSortedArrayWithoutDuplicates(int[] nums) {  //Runtime: O(log n)
        // The key observation is that the minimum element is in the unsorted portion of the array.
        // By checking the relationship between the middle element and the boundaries, narrow down the search space.

        int lo = 0, hi = nums.length - 1; // Initialize pointers for binary search

        while (lo < hi) { // Continue until the search space is narrowed to one element
            int mid = lo + (hi - lo) / 2; // Calculate the middle index to avoid overflow

            // If nums[mid] is less than nums[hi], this means:
            // - The right half is sorted.
            // - The minimum lies in the left portion, including nums[mid].
            if (nums[mid] < nums[hi]) {
                hi = mid; // Narrow down the search to the left half (including mid)
            } else {
                // Otherwise, nums[mid] >= nums[hi], which means:
                // - The minimum lies in the right portion (mid+1 to hi).
                lo = mid + 1; // Discard the left half including nums[mid]
            }
        }

        // At the end of the loop, lo == hi, pointing to the minimum element
        return nums[lo];
    }

    /**
     * Find the minimum element in a rotated sorted array.
     * nums = [3,3,1,3]
     * Output: 1
     *
     * @param nums
     * @return
     */
    public int findMinRotatedSortedArrayWithDuplicates(int[] nums) {
        if (nums.length == 1)
            return nums[0];

        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2; // Calculate the middle index to avoid overflow

            // If nums[mid] is less than nums[hi], this means:
            // - The right half is sorted.
            // - The minimum lies in the left portion, including nums[mid].
            if (nums[mid] < nums[hi]) {
                hi = mid; // Narrow down the search to the left half (including mid)
            } else if (nums[mid] > nums[hi]) {
                // Otherwise, nums[mid] >= nums[hi], which means:
                // - The minimum lies in the right portion (mid+1 to hi).
                lo = mid + 1; // Discard the left half including nums[mid]
            } else {  //if (nums[mid] == nums[hi]) is true,
                // either all elements between mid and hi are same or the minimum lies here.
                hi -= 1;  //We need to consider every element one by one.
            }
        }

        return nums[lo];
    }

    /**
     * Find the first and last index of a repetitive element in a sorted array.
     * nums = [2, 3, 5, 5, 5, 5, 5, 8, 9]. target = 5
     * Output: [2, 6]
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchFirstAndLastIndexOfTarget(int[] nums, int target) {
        // Perform binary search twice.
        int lo = 0, hi = nums.length - 1;
        // Initialize the necessary variables.
        int firstIndex = -1, lastIndex = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // If the target is found at the middle index
            if (target == nums[mid]) {
                // Store the current position as a potential first position
                firstIndex = mid;
                hi = mid - 1;
            } else if (target < nums[mid]) {
                hi = mid - 1;
            }
            else {
                lo = mid + 1;
            }
        }

        lo = 0;
        hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // If the target is found at the middle index
            if (target == nums[mid]) {
                // Store the current position as a potential last position
                lastIndex = mid;
                lo = mid + 1;
            } else if (target < nums[mid]) {
                hi = mid - 1;
            }
            else {
                lo = mid + 1;
            }
        }

        return new int[]{firstIndex, lastIndex};
    }

    /**
     * Determine the minimum eating speed for Koko so that she can eat all the bananas in h hours.
     * Given an array piles where each element represents the number of bananas in a pile,
     * find the minimum speed (bananas per hour) that Koko needs to eat all the bananas within h hours.
     * piles = [3, 6, 7, 11]
     * h = 8
     *
     * @param piles
     * @param h
     * @return
     */
    // Runtime: O(n log(max(piles)))
    // n is the number of piles.
    // max(piles) is the size of the largest pile, which determines the range for the binary search.
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt();

        while (left < right) { // We want to break the loop when left == right.
            int mid = left + (right - left) / 2;
            if (canFinish(piles, h, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canFinish(int[] piles, int h, int rate) {
        // The rate here means the number of bananas Koko eats per hour.
        int hours = 0;
        for (int pile : piles) {
            // Calculate the hours and check if it's within the expected hour given in the input.
            hours += (pile + rate - 1) / rate;
        }
        return hours <= h;


        // For that input above, if (speed) rate = 1,
        // To eat 3 bananas from the first pile, it would take 3 hours.
        // To eat 6 bananas from the second pile, it would take 6 hours... and so on.
        // This is very slow. It will take 3 + 6 + 7 + 11 = 27 hours to finish all bananas. But the expected hour is 8.
        // If speed = 2 (Koko eats 2 bananas per hour):
        // To eat 3 bananas from the first pile, it would take 2 hours.
        // To eat 6 bananas from the second pile, it would take 3 hours... and so on.
        // This is also slow.
        // The ideal speed is 4.
    }

    /**
     * Given an integer array nums and an integer k,
     * split nums into k non-empty sub-arrays such that the largest sum of any subarray is minimized.
     * nums = [7,2,5,10,8], k = 2
     * Output: 18
     * There are four ways to split nums into two sub-arrays.
     * The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two sub-arrays is only 18.
     *
     * @param nums
     * @param k
     * @return
     */
    public int splitArray(int[] nums, int k) {
        // Runtime: O(n log(sum - max)) where sum is the sum of all elements. max is the largest element in the array.

        // Set lower bound to the maximum value in the array
        // (since at least one subarray will need that much).
        // Set the upper bound to the sum of all elements (one subarray with all the elements).
        // For each midpoint in this range,
        // check if it’s possible to split the array into k sub-arrays with a sum not exceeding mid.
        int left = Arrays.stream(nums).max().getAsInt();
        int right = Arrays.stream(nums).sum();

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSplit(nums, k, mid)) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return left;
        // When low meets high, the binary search stops,
        // and low is the minimum possible maximum sum that meets the requirements.
    }

    private boolean canSplit(int[] nums, int k, int maxSum) {
        int currSum = 0;
        int subArrays = 1;

        for (int num : nums) {
            currSum += num;

            if (currSum > maxSum) {
                // When the running sum of the current subarray exceeds maxSum,
                // it means the current subarray cannot hold any more values without exceeding the limit.
                subArrays += 1;
                currSum = num;  // Start a new subarray with the current number

                if (subArrays > k) {
                    // If we exceed the allowed number of sub-arrays k,
                    // the maxSum is too small to divide the array as required.
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Given a sorted array of integers nums and an integer targetSum,
     * return the indices of the two numbers such that they add up to target.
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     *
     * @param nums
     * @param targetSum
     * @return
     */
    public int[] twoSum(int[] nums, int targetSum) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int sum = nums[lo] + nums[hi];
            if (sum == targetSum) {
                return new int[]{lo, hi};
            } else if (sum < targetSum) {
                lo = lo + 1;
            } else {
                hi = hi - 1;
            }
        }
        return new int[]{};
    }

    /**
     * Given a positive integer num, write a function to determine whether num is a perfect square.
     *
     * @param num
     * @return
     */
    public boolean isPerfectSquare(int num) {
        int lo = 0, hi = (int) Math.pow(num, 0.5);  //If Math.sqrt isn't allowed, we can use this.

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (num == (mid * mid))
                return true;
            else if (num < (mid * mid))
                hi = mid - 1;
            else
                lo = mid + 1;
        }

        return false;
    }

    /**
     * Given a sorted array where every element appears exactly twice,
     * except for one element which appears exactly once. Find this single element that appears only once.
     * Input: [1,1,2,3,3,4,4,8,8]
     * Output: 2
     *
     * @param nums
     * @return
     */
    public int singleNonDuplicate(int[] nums) {
        if (nums.length == 1)
            return nums[0];

        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid % 2 == 0) {  //The middle index is an even number
                if (nums[mid] == nums[mid + 1])  //the left side is sorted. So go right.
                    lo = mid + 2;
                else
                    hi = mid - 1;
            } else {  //The middle index is an odd number
                if (nums[mid] == nums[mid - 1])  //the right side is sorted. So go left.
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }

        return nums[lo];
    }

    /**
     * A peak element is an element that is strictly greater than its neighbors.
     * If the array contains multiple peaks, return the index to any of the peaks.
     * No adjacent elements are equal. There is a guaranteed peak.
     * nums = [1,2,1,3,5,6,4]
     * output: 5
     *
     * @param nums
     * @return
     */
    public int findPeakElementIndex(int[] nums) {
        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            // The right half looks to be in a descending slope.
            // This means the peak could be at mid or somewhere left.
            if (nums[mid] > nums[mid + 1]) {
                hi = mid;
            }
            else {
                lo = mid + 1;
            }
        }

        return lo;
    }
}
