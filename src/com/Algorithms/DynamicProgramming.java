package com.Algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    public int coinChangeFewestCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);  //Integer.MAX_VALUE doesn't work. So use amount + 1.
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (coin <= i)  //(coin > i) break; condition doesn't work.
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * You are given coins of different denominations and a total amount.
     * Return the number of combinations that make up that amount.
     * You may assume that you have infinite number of each kind of coin.
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * There are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * @param amount
     * @param coins
     * @return
     */
    public int coinChangeCombinations(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        //The outer loop is for coins because we need all possible combinations and not permutations.
        // If the outer loop is amount, the same combination will count multiple times.
        // The coins can come in different orders.
        // By keeping coins as the outer loop, we restrict the order.
        for (int coin : coins) {
            for (int i = 1; i < dp.length; i++) {
                if (coin <= i)
                    dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }


    /**
     * Two lists A and B are written on two separate horizontal lines.
     * If we draw a connecting line from A to B, the two numbers must be equal A[i] == B[j].
     * The connecting lines must not intersect.
     * Return the maximum number of connecting lines we can draw this way.
     * A = [2,5,1,2,5]
     * B = [10,5,2,1,5,2]
     * Output: 3
     *
     * @param A
     * @param B
     * @return
     */
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[A.length][B.length];
    }

    /**
     * Given an integer n, return the number of structurally unique BSTs
     * which has exactly n nodes of unique values from 1 to n.
     * Input: n = 3
     * Output: 5
     * @param n
     * @return
     */
    public int numTrees(int n) {
        if (n == 0 || n == 1)
            return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {  // At the position i, we need to try out every element from 1 to i keeping it as root
            for (int root = 1; root <= i; root++) { //When root is 1, the elements 0 to 1 becomes LST. The difference between i and root becomes the RST
                dp[i] += dp[root - 1] * dp[i - root];
            }
        }
        return dp[n];
    }

    public String longestPalindromicSubstring(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        String longestPalSubstring = "";
        int longestPalLength = 0;

        //The diagonal top to bottom cells are true because the length of the palindrome is 1.
        // The starting and ending characters are the same.
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            longestPalLength = 1;
            longestPalSubstring = s.substring(i, i + 1);
        }

        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                longestPalLength = 2; //length of palindrome is 2.
                longestPalSubstring = s.substring(i, i + 2);
            }
        }

        int j;
        for (int k = 2; k < n; k++) {
            //This is the window. j must always be greater than i. Having a for loop after i with j=i+2 does not work.
            for (int i = 0; i < n; i++) {
                j = i + k;
                if (j < n && s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;

                    if (j - i + 1 > longestPalLength) {
                        longestPalLength = j - i + 1;
                        longestPalSubstring = s.substring(i, j + 1);
                    }
                }
            }
        }
        return longestPalSubstring;
    }

    public int LCSLength(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[x.length()][y.length()];
    }

    public boolean isWildcardMatching(String s, String p) {
        int n = s.length(), m = p.length();

        //the first row and column will be dedicated to empty string and pattern respectively.
        boolean[][] dp = new boolean[n + 1][m + 1];

        //both the pattern and string are empty
        dp[0][0] = true;

        //string is empty. the pattern is * so we look at the prev column value.
        for (int j = 1; j <= m; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                //The following are the two main cases we need to work on.
                // If we remove ith character and jth character from s and p,
                // we check if the other characters are a match or not, comes from the prev row and col.
                if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[n][m];
    }

    public boolean isRegexMatching(String s, String p) {
        int n = s.length(), m = p.length();

        //the first row and column will be dedicated to empty string and pattern respectively.
        boolean[][] dp = new boolean[n + 1][m + 1];

        //both the pattern and string are empty
        dp[0][0] = true;

        //string is empty. the pattern is * so we look at the prev column value.
        for (int j = 1; j <= m; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[n][m];
    }

    /**
     * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
     * You have the following 3 operations permitted on a word:
     * 1) Insert a character
     * 2) Delete a character
     * 3) Replace a character
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     *
     * @param word1
     * @param word2
     * @return
     */
    public int editMinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];  //matrix of indices

        for (int r = 0; r <= word1.length(); r++) {  //Initialize the first column
            dp[r][0] = r;
        }

        for (int c = 0; c <= word2.length(); c++) {  //Initialize the first row
            dp[0][c] = c;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * Given a string s and a dictionary of strings wordDict,
     * return true if s can be segmented into a space-separated sequence of one or more dictionary words.
     * Input: s = "applepenapple", wordDict = ["apple","pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     * Note that you are allowed to reuse a dictionary word.
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (wordDict.contains(s.substring(j, i)) && dp[j]) {
                    dp[i] = true;
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

        dp[0] = true;

        for(int i = 1; i < nums.length; i++) {
            for(int j = 0; j < i; j++){
                if (nums[j] >= i - j && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[nums.length-1];
    }

    /**
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2.
     * Jump 1 step from index 0 to 1, then 3 steps to the last index.
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
     * Given an integer n, return the least number of perfect square numbers that sum to n.
     * A perfect square is an integer that is the square of an integer;
     * it is the product of some integer with itself.
     * For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
     * Input: n = 12
     * Output: 3
     * Explanation: 12 = 4 + 4 + 4.
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        if(n == 0)
            return dp[0];

        dp[1] = 1;
        if(n == 1)
            return dp[1];

        dp[2] = 2;
        if(n == 2)
            return dp[2];

        dp[3] = 3;
        if(n == 3)
            return dp[3];

        for (int i = 4; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; j <= i; j++) {
                if ((j * j) > i)
                    break;
                else
                    dp[i] = Math.min(dp[i], 1 + dp[i - (j * j)]);
            }
        }
        return dp[n];
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
        int[] dp = new int[n];

        int longest = Integer.MIN_VALUE;

        Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        // Pick maximum of all LIS values
        for (int i = 0; i < n; i++) {
            if (longest < dp[i]) {
                longest = dp[i];
            }
        }

        return longest;
    }

    /**
     * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i],
     * obtaining a profit of profit[i].
     * You're given the startTime, endTime and profit arrays,
     * return the maximum profit you can take such that there are no two jobs in the subset with overlapping time range.
     * If you choose a job that ends at time X you will be able to start another job that starts at time X.
     *
     * @param startTime
     * @param endTime
     * @param profit
     * @return
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++){
            jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }

        Arrays.sort(jobs, Comparator.comparingInt(o -> o[1]));

        int[] dp = new int[n]; //profit array
        dp[0] = jobs[0][2];

        for (int i = 1; i < n; i++){
            dp[i] = dp[i - 1];
            int lo = 0, hi = i - 1;
            int prev = 0;

            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (jobs[mid][1] <= jobs[i][0]) {
                    prev = dp[mid];
                    lo = mid + 1;
                }
                else {
                    hi = mid - 1;
                }
            }
            dp[i] = Math.max(dp[i], jobs[i][2] + prev);  //max of dp[i] and current profit + profit until now.
        }

        return dp[n - 1];
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

    /**
     * Given a binary array that has exactly one island (i.e., one or more connected land cells).
     * One cell is a square with side length 1. Determine the perimeter of the island.
     * Input:
     * [
     *  [0,1,0,0],
     *  [1,1,1,0],
     *  [0,1,0,0],
     *  [1,1,0,0]
     * ]
     * Output: 16
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int rows = grid.length, cols = grid[0].length, perimeter = 0;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1) {
                    for (int[] dir : directions) {
                        int r = row + dir[0];
                        int c = col + dir[1];

                        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == 0) {
                            perimeter += 1;
                        }
                    }
                }
            }
        }
        return perimeter;
    }
}
