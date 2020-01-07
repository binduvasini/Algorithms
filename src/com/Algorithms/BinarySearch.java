package com.Algorithms;

public class BinarySearch {
    int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (target == nums[mid])
                return mid;

            else if (nums[mid] < nums[hi]) { //the right side is sorted for sure.
                if (target > nums[mid] && target <= nums[hi]) //we need these two conditions to determine the target lies in nums[mid...hi]
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
}
