package com.Algorithms;

public class BinarySearch {
    public int binarySearchIterative(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {  //Because we go from lo till hi (lo <= hi), we do mid - 1 and mid + 1.
            int mid = lo + (hi - lo) / 2; //To avoid integer overflow. sometimes low+high will be greater than 2147483647
            if (target < nums[mid])
                hi = mid - 1;
            else if (target > nums[mid])
                lo = mid + 1;
            else
                return mid; // key found
        }
        return -1;  // key not found.
    }

    /**
     * Find the value at pivot index in a rotated sorted array. In other words, find the minimum element in a rotated sorted array.
     * nums = [12, 14, 1, 5, 8, 9]
     * Output: 1
     * @param nums
     * @return
     */
    public int findMinRotatedSortedArray(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            if (nums[lo] < nums[hi])
                return nums[lo];

            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < nums[hi]) {
                if (mid > lo && nums[mid - 1] > nums[mid])
                    return nums[mid];
                hi = mid - 1;
            }
            else {
                if (mid < nums.length - 1 && nums[mid + 1] < nums[mid])
                    return nums[mid + 1];
                lo = mid + 1;
            }
        }
        return nums[lo];
    }

    /**
     * Search an element in a rotated sorted array.
     * @param nums
     * @param target
     * @return
     */
    public int searchRotatedSortedArray(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (target == nums[mid])
                return mid;

            else if (nums[mid] < nums[hi]) { //the right side is sorted for sure.
                if (target > nums[mid] && target <= nums[hi]) //we need these two conditions to determine whether the target lies in nums[mid...hi]
                    lo = mid + 1;

                else
                    hi = mid - 1;

            } else { //the left side is sorted for sure.
                if (target < nums[mid] && target >= nums[lo])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            }
        }
        return -1;
    }

    public int[] searchFirstAndLastOccurrenceOfTarget(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        int firstIndex = -1, lastIndex = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                firstIndex = mid;
                hi = mid - 1;
            } else if (target > nums[mid])
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        lo = 0;
        hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                lastIndex = mid;
                lo = mid + 1;
            } else if (target > nums[mid])
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        return new int[]{firstIndex, lastIndex};
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
            if ((mid * mid) == num)
                return true;
            else if ((mid * mid) < num)
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        return false;
    }

    /**
     * Given a sorted array where every element appears exactly twice, except for one element which appears exactly once. Find this single element that appears only once.
     * Input: [1,1,2,3,3,4,4,8,8]
     * Output: 2
     *
     * @param nums
     * @return
     */
    public int singleNonDuplicate(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        int n = nums.length;
        int lo = 0, hi = n - 1;
        while (lo < hi) {  //
            int mid = lo + (hi - lo) / 2;
            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid + 1])
                    lo = mid + 2;
                else
                    hi = mid - 1;
            } else {
                if (nums[mid] == nums[mid - 1])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }
        return nums[lo];
    }
}
