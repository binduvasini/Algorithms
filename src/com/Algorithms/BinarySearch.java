package com.Algorithms;

public class BinarySearch {
    int searchRotatedArray(int[] nums, int target) {
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
}
