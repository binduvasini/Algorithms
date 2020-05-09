package com.Algorithms;

public class BinarySearch {
    static int binarySearchIterative(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2; //To avoid integer overflow. sometimes low+high will be greater than 2147483647
            if (target < a[mid])
                high = mid - 1;
            else if (target > a[mid])
                low = mid + 1;
            else
                return mid; // key found
        }
        return -1;  // key not found.
    }

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

    public static void main(String[] args) {
        System.out.println(binarySearchIterative(new int[]{3, 2, 1, 10, 23, 22, 21}, 10));
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
}
