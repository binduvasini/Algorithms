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
     * Search an element in a rotated sorted array with duplicates.
     * nums = [1,1,1,1,5,1,1]. target = 5.
     * Output: 4
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
     * Find the first and last index of a repetitive element in a sorted array.
     * nums = [2, 3, 5, 5, 5, 5, 5, 8, 9]. target = 5
     * Output: [2, 6]
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
     * Find the value at pivot index in a rotated sorted array.
     * In other words, find the minimum element in a rotated sorted array.
     * nums = [12, 14, 1, 5, 8, 9]
     * Output: 1
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
     * Find the value at pivot index in a rotated sorted array.
     * In other words, find the minimum element in a rotated sorted array.
     * nums = [3,3,1,3]
     * Output: 1
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
