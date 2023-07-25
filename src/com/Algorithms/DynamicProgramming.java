package com.Algorithms;

import java.util.Arrays;

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

    boolean isWildcardMatching(String s, String p) {
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

    boolean isRegexMatching(String s, String p) {
        int n = s.length(), m = p.length();
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
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {  //Initialize the first column
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {  //Initialize the first row
            dp[0][j] = j;
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
}
