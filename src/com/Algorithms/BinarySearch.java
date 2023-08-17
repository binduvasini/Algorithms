package com.Algorithms;

public class BinarySearch {
    public int binarySearchIterative(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {  //Because we go from lo till hi (lo <= hi), we do mid - 1 and mid + 1.
            int mid = lo + (hi - lo) / 2;  //To avoid integer overflow. Sometimes low+high will be > 2147483647
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
     * Search an element in a rotated sorted array.
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchRotatedSortedArrayWithoutDuplicates(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (target == nums[mid])
                return mid;

            if (nums[lo] <= nums[mid]) { //the left side is sorted for sure.
                if (target < nums[mid] && target >= nums[lo])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            } else { //the right side is sorted for sure.
                if (target > nums[mid] && target <= nums[hi])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }

        return target == nums[lo] ? lo : -1;
    }

    /**
     * Find the value at pivot index in a rotated sorted array.
     * In other words, find the minimum element in a rotated sorted array.
     * nums = [12, 14, 1, 5, 8, 9]
     * Output: 1
     *
     * @param nums
     * @return
     */
    public int findMinRotatedSortedArrayWithoutDuplicates(int[] nums) {
        if (nums.length == 1)
            return nums[0];

        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            if (nums[lo] < nums[hi])  //For case: nums = [1,2,3] when the array is already sorted in ascending order.
                return nums[lo];

            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < nums[hi]) {  //The minimum is in the left half.
                if (mid > lo && nums[mid - 1] > nums[mid])  //If the mid element is the minimum. Have a boundary check.
                    return nums[mid];
                hi = mid - 1;
            }
            else {  //The minimum is in the right half.
                if (mid < nums.length - 1 && nums[mid + 1] < nums[mid])  //If mid+1 element is the minimum.
                    // Have a boundary check.
                    return nums[mid + 1];
                lo = mid + 1;
            }
        }

        return nums[lo];  //For any other case, return the lo element.
    }

    /**
     * Search an element in a rotated sorted array with duplicates.
     * nums = [1,1,1,1,5,1,1]. target = 5.
     * Output: 4
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchRotatedSortedArrayWithDuplicates(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (target == nums[mid])
                return mid;

            if (nums[lo] < nums[mid]) { //the left side is sorted for sure.
                if (target < nums[mid] && target >= nums[lo])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            } else if (nums[mid] < nums[hi]) { //the right side is sorted for sure.
                if (target > nums[mid] && target <= nums[hi])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
            else //if (nums[mid] == nums[hi]) is true,
                // either all elements between mid and hi are same or the target lies here.
                hi -= 1;  //We need to consider every element one by one.
        }

        return -1;
    }

    /**
     * Find the value at pivot index in a rotated sorted array.
     * In other words, find the minimum element in a rotated sorted array.
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
            if (nums[lo] < nums[hi])
                return nums[lo];

            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < nums[hi]) {
                if (mid > lo && nums[mid - 1] > nums[mid])
                    return nums[mid];
                hi = mid - 1;
            }
            else if (nums[mid] > nums[hi]) {
                if (mid < nums.length - 1 && nums[mid + 1] < nums[mid])
                    return nums[mid + 1];
                lo = mid + 1;
            }
            else  //if (nums[mid] == nums[hi]) is true,
                // either all elements between mid and hi are same or the minimum lies here.
                hi -= 1;  //We need to consider every element one by one.
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
        int lo = 0, hi = nums.length - 1;
        int firstIndex = -1, lastIndex = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                firstIndex = mid;
                hi = mid - 1;
            }
            else if (target < nums[mid])
                hi = mid - 1;
            else
                lo = mid + 1;
        }

        lo = 0;
        hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                lastIndex = mid;
                lo = mid + 1;
            }
            else if (target < nums[mid])
                hi = mid - 1;
            else
                lo = mid + 1;
        }

        return new int[]{firstIndex, lastIndex};
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
        int[] output = new int[2];

        while (lo <= hi) {
            int sum = nums[lo] + nums[hi];
            if (sum == targetSum) {
                output[0] = lo;
                output[1] = hi;
                break;
            }
            else if (sum < targetSum) {
                lo = lo + 1;
            }
            else {
                hi = hi - 1;
            }
        }
        return output;
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
     *
     * @param nums
     * @return
     */
    public int findPeakElementIndex(int[] nums) {
        int lo = 0, hi = nums.length - 1;

        while(lo <= hi){
            if(lo == hi)
                return lo;

            int mid = lo + (hi - lo) / 2;

            if (nums[mid] > nums[mid + 1])
                hi = mid;
            else
                lo = mid + 1;
        }

        return -1;
    }

    /**
     * Given an array nums with n objects colored red, white, or blue,
     * sort them in-place so that objects of the same color are adjacent,
     * with the colors in the order red, white, and blue.

     * We will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

     * Input: nums = [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     *
     * @param nums
     */
    public void sortColors(int[] nums) {  //Dutch national flag problem
        int lo = 0, mid = 0, hi = nums.length - 1;

        while (mid <= hi) {  //Look at the value in the middle position.
            if (nums[mid] == 0) {  //If it's 0, swap it with nums[lo]. increment lo and mid.
                swap(nums, lo, mid);
                lo += 1;
                mid += 1;
            }
            else if (nums[mid] == 2) {  //If it's 2, swap it with nums[hi]. decrement hi.
                swap(nums, hi, mid);
                hi -= 1;
            }
            else {  //If it's 1, no swapping is required. increment mid.
                mid += 1;
            }
        }
    }

    private void swap(int[] nums, int ind1, int ind2) {
        int temp = nums[ind1];
        nums[ind1] = nums[ind2];
        nums[ind2] = temp;
    }
}
