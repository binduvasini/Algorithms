package com.Algorithms;

import java.util.Arrays;

public class DynamicProgramming {
    /**
     * You are given coins of different denominations and a total amount. Return the fewest number of coins that you need to make up that amount. If that amount cannot be made up by any combination of the coins, return -1.
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
     * You are given coins of different denominations and a total amount. Return the number of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
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
        // If the outer loop is amount, the same combination will counted multiple times. The coins can come in different orders.
        // By keeping coins as the outer loop, we restrict the order.
        for (int coin : coins) {
            for (int i = 1; i < dp.length; i++) {
                if (coin <= i)
                    dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}
