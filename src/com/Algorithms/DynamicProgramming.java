package com.Algorithms;

import java.util.*;

public class DynamicProgramming {
    /**
     * You are given coins of different denominations and a total amount.
     * Return the fewest number of coins that you need to make up that amount.
     * If that amount cannot be made up by any combination of the coins, return -1.
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     * 11 = 5 + 5 + 1
     *
     * @param coins
     * @param amount
     * @return
     */
    // Runtime: O(n•m), where n is the amount and m is the number of coin denominations.
    public int coinChangeFewestCoins(int[] coins, int amount) {
        // Define a DP array dp where dp[i] represents the minimum number of coins needed to make an amount i.
        int[] dp = new int[amount + 1];
        // Initialize dp array with a value larger than the maximum possible answer
        Arrays.fill(dp, amount + 1);  // Integer.MAX_VALUE doesn't work. So use amount + 1.

        // Base case: 0 coins are needed to make an amount of 0.
        dp[0] = 0;

        // Iterate through all amounts from 1 to `amount`
        for (int i = 1; i < dp.length; i++) {
            // Check all coin denominations
            for (int coin : coins) {
                // Check if it's possible to use this coin.
                if (i - coin >= 0) {
                    // Is the current amount minus the coin is a valid number?
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // After filling the DP array, if dp[amount] remains a large initial value,
        // it means we cannot form that amount with the given coins.
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * Max sum in an array such that no two elements are adjacent.
     * nums = [2, 7, 9, 3, 1]
     * Output: 2 + 9 + 1 = 12
     *
     * @param nums
     * @return
     */
    public int houseRobbery(int[] nums) {
        // Create a DP table to store the maximum sum we can rob up to each house
        // To determine the value of dp[i], we either skip the house i or rob the house i.
        int[] dp = new int[nums.length];

        // Base cases:
        // If we rob only the first house, the max sum is just nums[0]
        dp[0] = nums[0];
        // If we rob up to the second house, we take the maximum of robbing the first or second house
        dp[1] = Math.max(nums[0], nums[1]);

        // Fill the DP table for the rest of the houses
        for (int i = 2; i < nums.length; i++) {
            // For each house i, we have two choices:
            // 1. Skip the current house: In this case, the max sum is the same as dp[i-1].
            // 2. Rob the current house: Add nums[i] to dp[i-2] (because we can't rob adjacent houses).
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        // The last entry in the DP table represents the maximum sum we can rob
        return dp[nums.length - 1];
    }

    /**
     * Given a string s and a dictionary of strings wordDict,
     * return true if s can be segmented into a space-separated sequence of one or more dictionary words.
     * Input: s = "applepenapple", wordDict = ["apple","pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     * Note that you are allowed to reuse a dictionary word.
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];

        dp[0] = true;

        for (int end = 1; end <= s.length(); end++) {
            for (int start = 0; start < end; start++) {
                if (wordDict.contains(s.substring(start, end)) && dp[start]) {
                    dp[end] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * You are given an integer array nums.
     * You are initially positioned at the array's first index, and
     * each element in the array represents your maximum jump length at that position.
     * Return true if you can reach the last index, or false otherwise.
     * Input: nums = [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];

        dp[0] = true;  //The starting point is reachable.

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (i - j <= nums[j] && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[nums.length - 1];
    }

    /**
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2.
     * Jump 1 step from index 0 to 1, then 3 steps to the last index.
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];

        dp[0] = 0;

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (nums[j] >= i - j) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length - 1];
    }

    /**
     * Given an integer array, return the length of the length of the longest increasing subsequence.
     * nums = [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     *
     * @param nums
     * @return
     */
    public int longestIncreasingSubsequenceLength(int[] nums) {
        int n = nums.length;
        // dp[i] represents the length of the longest increasing subsequence that ends at index i.
        int[] dp = new int[n];

        // Initialize each element in dp to 1. The minimum length of LIS ending at any index is 1 (the element itself).
        Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            // To build the LIS ending at nums[i], check all the previous elements
            for (int j = 0; j < i; j++) {
                // If the current element is greater than previous elements, we can append the current element.
                if (nums[i] > nums[j]) {
                    // If we can append nums[i] to an increasing subsequence ending at nums[j],
                    // then the length of the LIS ending at nums[i] would be dp[j] + 1.
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // Pick maximum of all LIS values
        int longestLIS = 0;
        for (int len : dp) {
            longestLIS = Math.max(longestLIS, len);
        }
        return longestLIS;
    }

    /**
     * Given a binary matrix, find the largest square containing only 1s and return its area.
     * Input:
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     * Output: 4
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0)
            return 0;
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        int rows = matrix.length, cols = matrix[0].length, maxSide = 0;

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if (matrix[r - 1][c - 1] == '1') {
                    dp[r][c] = Math.min(Math.min(dp[r][c - 1], dp[r - 1][c]), dp[r - 1][c - 1]) + 1;
                    maxSide = Math.max(maxSide, dp[r][c]);
                }
            }
        }

        return maxSide * maxSide;
    }

    /**
     * Given a binary matrix, return how many square sub-matrices have all 1s.
     * Input: matrix =
     * [
     * [0,1,1,1],
     * [1,1,1,1],
     * [0,1,1,1]
     * ]
     * Output: 15
     * There are 10 squares of side 1.
     * There are 4 squares of side 2.
     * There is  1 square of side 3.
     * Total number of squares = 10 + 4 + 1 = 15.
     *
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        int rows = matrix.length, cols = matrix[0].length, count = 0;

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                if (matrix[r - 1][c - 1] == 1) {
                    dp[r][c] = Math.min(Math.min(dp[r][c - 1], dp[r - 1][c]), dp[r - 1][c - 1]) + 1;
                    count += dp[r][c];
                }
            }
        }
        return count;
    }
}
