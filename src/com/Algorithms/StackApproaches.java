package com.Algorithms;

import java.util.ArrayDeque;
import java.util.HashMap;

public class StackApproaches {

    /**
     * For every element in the array, find its next greater element print it in this value’s position or print -1. The Next greater element of a number is the first greater number to its right.
     * Input: [4, 1, 6, 5, 9, 3, 2, 7]
     * Output: [6, 6, 9, 9, -1, 7, 7, -1]
     * @param nums
     * @return
     */
    public int[] nextGreaterElementsWithoutDuplicates(int[] nums) {
        int[] nextGreaterElements = new int[nums.length];
        ArrayDeque<Integer> stack = new ArrayDeque<>();  //Use ArrayDeque class which will at least restrict the access to one of the ends, instead of Stack (that offers search & elementAt)
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            while (!stack.isEmpty() && num >= stack.peek()) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        for (int i = 0; i < nums.length; i++) {
            nextGreaterElements[i] = map.getOrDefault(nums[i], -1);
        }
        return nextGreaterElements;
    }

    /**
     * Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: [2,-1,2]
     * Explanation: The first 1's next greater number is 2;
     * The number 2 can't find next greater number;
     * The second 1's next greater number needs to search circularly, which is also 2.
     * @param nums
     * @return
     */
    public int[] nextGreaterElementsWithDuplicates(int[] nums) {
        int[] nextGreaterElements = new int[nums.length];
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < 2 * n; i++) {
            int num = nums[i % n];
            while (!stack.isEmpty() && num > stack.peek()[1]) {
                map.put(stack.pop()[0], num);
            }
            stack.push(new int[]{i, num});
        }
        for (int i = 0; i < n; i++) {
            nextGreaterElements[i] = map.getOrDefault(i, -1);
        }
        return nextGreaterElements;
    }

    /**
     * Given a string containing only three types of characters: '(', ')'
     * and '*', write a function to check whether this string is valid.
     * We define the validity of a string by these rules:
     * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * 3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
     * 4. '*' could be treated as a single right parenthesis ')' 
     * or a single left parenthesis '(' or an empty string.
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
     * Given a list of daily temperatures T,
     * return a list such that,
     * for each day in the input, tells you how many days you would have to wait until a warmer temperature.
     * If there is no future day for which this is possible, put 0.
     * Input: T = [73, 74, 75, 71, 69, 72, 76, 73]
     * Output: [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        int[] output = new int[T.length];
        ArrayDeque<int[]> stack = new ArrayDeque<>();  //store the index and value as a pair.

        for (int i = 0; i < T.length; i++) {
            int currTemp = T[i];
            //Dig into the stack only when the current temperature is warmer than the immediate previous temperature
            while (!stack.isEmpty() && currTemp > stack.peek()[1]) {
                int[] prevTemp = stack.pop();
                //From the current index, the immediate previous index just needs to wait for 1 day.
                // Going further down, we will have to wait current index - the popped index.
                output[prevTemp[0]] += i - prevTemp[0];
            }
            stack.push(new int[]{i, currTemp});
        }
        return output;
    }

    /**
     * StockSpanner collects the daily price quotes for a stock and
     * returns the span of that stock's price for the current day.
     * The span of the stock's price today is defined as the maximum number of previous days
     * for which the price of the stock was less than or equal to today's price.
     * For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 58, 75, 85],
     * then the stock spans would be [1, 1, 1, 2, 1, 4, 6].
     *
     * @param price
     * @return
     */
    ArrayDeque<int[]> stocks = new ArrayDeque<>();  //Store the spanValue and the current price as a pair.

    public int stockSpanner(int price) {
        int spanOfStock = 1;
        //Dig into the stack only when this price is greater than the immediate previous stock price.
        while (!stocks.isEmpty() && price >= stocks.peek()[1]) {
            int[] prevPrice = stocks.pop();
            spanOfStock += prevPrice[0];
        }
        stocks.push(new int[]{spanOfStock, price});
        return spanOfStock;
    }

}
