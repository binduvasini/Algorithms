package com.Algorithms;

import java.util.ArrayDeque;

public class StackApproaches {

    /**
     * Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:
     * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * 3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
     * 4. '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
     * 5. An empty string is also valid.
     */
    public boolean checkValidString(String s) {
        ArrayDeque<Integer> openStack = new ArrayDeque<>();
        ArrayDeque<Integer> starStack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                openStack.push(i);
            else if (c == '*')
                starStack.push(i);
            else {
                if (!openStack.isEmpty())
                    openStack.pop();
                else if (!starStack.isEmpty())
                    starStack.pop();
                else
                    return false;
            }
        }
        while (!openStack.isEmpty() && !starStack.isEmpty()) {
            if (openStack.pop() > starStack.pop())
                return false;
        }
        return openStack.isEmpty();
    }

    /**
     * Given a list of daily temperatures T, return a list such that, for each day in the input, tells you how many days you would have to wait until a warmer temperature. If there is no future day for which this is possible, put 0 instead.
     * Input: T = [73, 74, 75, 71, 69, 72, 76, 73]
     * Output: [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        int[] output = new int[T.length];
        ArrayDeque<int[]> stack = new ArrayDeque<>();

        for (int i = 0; i < T.length; i++) {
            int currTemp = T[i];
            while (!stack.isEmpty() && currTemp > stack.peek()[1]) {
                int[] prevTemp = stack.pop();
                output[prevTemp[0]] += i - prevTemp[0];  //From the current index, the immediate previous index just needs to wait for 1 day. Going further down, we will have to wait current index - the popped index.
            }
            stack.push(new int[]{i, currTemp});
        }
        return output;
    }

    /**
     * Write a class StockSpanner which collects daily price quotes for a stock, and returns the span of that stock's price for the current day.
     * The span of the stock's price today is defined as the maximum number of previous days for which the price of the stock was less than or equal to today's price.
     * For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 58, 75, 85], then the stock spans would be [1, 1, 1, 2, 1, 4, 6].
     *
     * @param price
     * @return
     */
    ArrayDeque<int[]> stocks = new ArrayDeque<>();

    public int nextPrice(int price) {
        int spanOfStock = 1;
        while (!stocks.isEmpty() && price >= stocks.peek()[1]) {
            int[] prevPrice = stocks.pop();
            spanOfStock += prevPrice[0];
        }
        stocks.push(new int[]{spanOfStock, price});
        return spanOfStock;
    }

}
