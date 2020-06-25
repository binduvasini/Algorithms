package com.Algorithms;

import java.util.*;
import java.util.regex.Pattern;

public class Playground {

    public static void main(String[] args) {

    }

    static void find3Numbers(int[] A,
                             int arr_size, int sum) {
        // Fix first element as A[i]
        for (int i = 0; i < arr_size - 2; i++) {

            // Find pair in subarray A[i+1..n-1]
            // with sum equal to sum - A[i]
            HashSet<Integer> s = new HashSet<Integer>();
            int curr_sum = sum - A[i];
            for (int j = i + 1; j < arr_size; j++) {
                if (s.contains(curr_sum - A[j]) && curr_sum - A[j] != (int) s.toArray()[s.size() - 1]) {
                    System.out.printf("Triplet is %d, %d, %d", A[i],
                            A[j], curr_sum - A[j]);
                    System.out.println();
                }
                s.add(A[j]);
            }
        }
    }

    static boolean isPalindrome(String str) {
        return isPalindrome(str, 0, str.length() - 1);
    }

    static boolean isPalindrome(String str, int start, int end) {
        if (start >= end)
            return true;
        if (str.charAt(start) != str.charAt(end))
            return false;
        return isPalindrome(str, start + 1, end - 1);
    }

    private static int minDays(int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        int target = grid.length * grid[0].length;
        int cnt = 0, res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    q.offer(new int[]{i, j});
                    cnt++;
                }
            }
        }
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!q.isEmpty()) {
            int size = q.size();
            if (cnt == target)
                return res;
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for (int[] dir : dirs) {
                    int ni = cur[0] + dir[0];
                    int nj = cur[1] + dir[1];
                    if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length && grid[ni][nj] == 0) {
                        cnt++;
                        q.offer(new int[]{ni, nj});
                        grid[ni][nj] = 1;
                    }
                }
            }
            res++;
        }
        return -1;
    }

    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int total = 0;
        for (int i = 0; i < nums.length; i++) total += nums[i];
        if (total % 2 != 0) return false;
        List<Integer> list = new ArrayList<Integer>();
        Arrays.sort(nums);
        return helper(nums, list, 0, 0, total);
    }

    public boolean helper(int[] nums, List<Integer> list, int pos, int sum, int total) {
        if (total == 2 * sum) return true;
        if (total < 2 * sum) return false;
        boolean res = false;
        for (int i = pos; i < nums.length; i++) {
            if (i == pos || nums[i - 1] != nums[i]) {
                list.add(nums[i]);
                res = res || helper(nums, list, i + 1, sum + nums[i], total);
                if (res) return true;
                list.remove(list.size() - 1);
            }
        }
        return false;
    }
}