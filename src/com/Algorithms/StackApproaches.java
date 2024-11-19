package com.Algorithms;

import java.util.Stack;
import java.util.*;

public class StackApproaches {

    /**
     * For every element in the array, find its next greater element print it in this value’s position or print -1.
     * The Next greater element of a number is the first greater number to its right.
     * Input: [4, 1, 6, 5, 9, 3, 2, 7]
     * Output: [6, 6, 9, 9, -1, 7, 7, -1]
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElementsWithoutDuplicates(int[] nums) {
        int[] nextGreaterElements = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<>();  //Use ArrayDeque class which will
        // restrict the access to one of the ends, instead of Stack (that offers search & elementAt)
        Map<Integer, Integer> map = new HashMap<>();

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
     * Given a circular array (the next element of the last element is the first element of the array),
     * print the Next Greater Number for every element.
     * The Next Greater Number of a number x is the first greater number to its traversing-order next in the array,
     * which means you could search circularly to find its next greater number.
     * If it doesn't exist, output -1 for this number.
     * Example 1:
     * Input: [1,2,1]
     * Output: [2,-1,2]
     * Explanation: The first 1's next greater number is 2;
     * The number 2 can't find next greater number;
     * The second 1's next greater number needs to search circularly, which is also 2.
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElementsWithDuplicates(int[] nums) {
        int[] nextGreaterElements = new int[nums.length];
        Deque<int[]> stack = new ArrayDeque<>();  //[index, number]
        Map<Integer, Integer> map = new HashMap<>();
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
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     * An input string is valid if:
     * Open brackets are closed by the same type of brackets.
     * Open brackets are closed in the correct order.
     * Every close bracket has a corresponding open bracket of the same type.
     *
     * @param s
     * @return
     */
    public boolean isValidParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    /**
     * Given a string containing only three types of characters: '(', ')'
     * and '*', write a function to check whether this string is valid.
     * We define the validity of a string by these rules:
     * 1. Any left parenthesis'('must have a corresponding right parenthesis')'.
     * 2. Any right parenthesis')'must have a corresponding left parenthesis'('.
     * 3. Left parenthesis'('must go before the corresponding right parenthesis')'.
     * 4. '*'could be treated as a single right parenthesis')'
     * or a single left parenthesis'('or an empty string.
     * 5. An empty string is also valid.
     *
     */
    public boolean checkValidParenthesesWithWildcard(String s) {
        Deque<Integer> openStack = new ArrayDeque<>();
        Deque<Integer> starStack = new ArrayDeque<>();

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
     * Given a list of daily temperatures T, return a list such that,
     * for each day in the input, tells you how many days you would have to wait until a warmer temperature.
     * If there is no future day for which this is possible, put 0.
     * Input: T = [73, 74, 75, 71, 69, 72, 76, 73]
     * Output: [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] output = new int[temperatures.length];

        // Initialize a stack to store the index, where the index is the day index.
        Deque<Integer> stack = new ArrayDeque<>();

        // Loop through the array of temperatures
        for (int i = 0; i < temperatures.length; i++) {
            int currTemp = temperatures[i];

            //Dig into the stack only when the current temperature is warmer than the immediate previous temperature
            while (!stack.isEmpty() && currTemp > temperatures[stack.peek()]) {
                // Pop the previous day's indices.
                int prevIndex = stack.pop();

                // The number of days to wait is the current index - the popped index.
                // We store this value in the output array at the index prevIndex,
                // because prevIndex is the day that needs to wait for a warmer temperature.
                output[prevIndex] = i - prevIndex;
            }

            // Push the current day's index onto the stack
            stack.push(i);
        }
        return output;
    }

    /**
     * Given string num representing a non-negative integer num, and an integer k,
     * return the smallest possible integer after removing k digits from num.
     * num = "1432219", k = 3
     * Output: "1219"
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        // To minimize the number, remove the larger digits if a smaller digit follows them.
        // The stack helps us maintain the digits of the number in increasing order as we traverse num.
        Stack<Character> stack = new Stack<>();

        // Whenever a larger digit appears at the top of the stack,
        // and a smaller digit is encountered in the input, remove the larger digit from the stack
        for (char digit : num.toCharArray()) {
            while (!stack.isEmpty() && stack.peek() > digit && k != 0) {
                stack.pop();
                k -= 1;
            }
            stack.push(digit);
        }

        // If there are still digits to remove, remove them.
        while (k != 0) {
            stack.pop();
            k -= 1;
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        result.reverse();

        return result.toString();

        // After removing digits, the stack contains the smallest possible number but may have leading zeros.
        // Remove them before returning the result.
    }

    /**
     * StockSpanner collects the daily price quotes for a stock and
     * returns the span of that stock's price for the current day.
     * The span of the stock's price in one day is the maximum number of consecutive days
     * (starting from that day and going backward)
     * for which the stock price was less than or equal to the price of that day.
     * Example: If the prices of the stock in the last four days is [7,34,1,2] and the price of the stock today is 8,
     * then the span of today is 3
     * because starting from today, the price of the stock was less than or equal 8 for 3 consecutive days.
     *
     * @param price
     * @return
     */
    Deque<int[]> stack = new ArrayDeque<>();  //[current price, spanValue] as a pair.

    public int stockSpanner(int price) {
        int span = 1;  //By default, the span is 1. Meaning this stock price has been there at least for 1 day.

        //Are there stock prices in the stack lesser than the current day's price? Pop them and add the span.
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }

        stack.push(new int[]{price, span});

        return span;
    }

    /**
     * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
     * return the area of the largest rectangle in the histogram.
     * Input: heights = [2,1,5,6,2,3]
     * Output: 10
     * The above is a histogram where width of each bar is 1.
     * The largest rectangle is shown in the red area, which has an area = 10 units.
     *
     * @param heights
     * @return
     */
    public int largestRectangleInHistogram(int[] heights) {
        int maxArea = Integer.MIN_VALUE;
        Deque<int[]> stack = new ArrayDeque<>();  //index and current element as a pair

        for (int i = 0; i < heights.length; i++) {
            int start = i;  //There is a way to extend it backwards. So maintain a start index.
            int currHeight = heights[i];

            //If the heights are in increasing order, we don't have to pop from the stack.
            while (!stack.isEmpty() && currHeight < stack.peek()[1]) {
                int[] prevHeight = stack.pop();
                maxArea = Math.max(maxArea, prevHeight[1] * (i - prevHeight[0]));
                start = prevHeight[0];
            }
            stack.push(new int[]{start, currHeight});
        }

        while (!stack.isEmpty()) {
            int[] height = stack.pop();
            maxArea = Math.max(maxArea, height[1] * (heights.length - height[0]));
        }

        return maxArea;
    }

    /**
     * Given a string s representing a valid expression, implement a basic calculator to evaluate it,
     * and return the result of the evaluation.
     * Input: s = "(1+(4+5+2)-3)+(6+8)"
     * Output: 23
     *
     * @param s
     * @return
     */
    public int calculator(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int number = 0;  //Need this to store the number in the equation.
        int sign = 1;  //Need this to keep track of the sign so far.
        int result = 0;  //Need this to store the result so far.

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                number = 10 * number + (c - '0');  //If the digit is 217, we look at each character.
                // So we need to build the full number by doing this.
            } else if (c == '+') {  //The number is over. We have encountered a sign.
                result += sign * number;  //Now it's time to update the result because
                // we are going to update the sign and the number.
                sign = 1;  //Whatever follows next, needs to hold this sign.
                number = 0;  //We finished looking at the number. Reset it so that the next number is ready to be stored.
            } else if (c == '-') {
                result += sign * number;
                sign = -1;
                number = 0;
            } else if (c == '(') {  //Now it's time to use the stack.
                //Push the result first, then the sign. There is nothing to do with the number here.
                stack.push(result);
                stack.push(sign);
                //reset the sign and result
                sign = 1;
                result = 0;  //A new result needs to be calculated using the equation inside this parenthesis.
            } else if (c == ')') {
                result += sign * number;
                result *= stack.pop();   //pop the sign before the number in the close parenthesis
                result += stack.pop();   //pop the previously calculated result
                number = 0;
            }
        }
        if (number != 0) {  //In case there is no parentheses in the equation. We need to calculate the result.
            result += sign * number;
        }
        return result;
    }

    /**
     * Evaluate Reverse Polish Notation.
     * tokens = ["2", "1", "+", "3", "*"]
     * Evaluate the expression: (2 + 1) * 3 = 9.
     *
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        // Push numbers onto the stack.
        // For operators, pop the top two numbers, perform the operation, and push the result back into the stack.
        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                // Pop the top two elements for operation
                int b = stack.pop();
                int a = stack.pop();

                // Perform the operation and push the result back onto the stack
                int result = applyOperation(a, b, token);
                stack.push(result);
            } else {
                // Push numbers onto the stack
                stack.push(Integer.parseInt(token));
            }
        }

        // The final result will be at the top of the stack
        return stack.pop();
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private int applyOperation(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b; // Integer division
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    /**
     * Design a stack that supports push, pop, top like a regular stack.
     * getMin(): Retrieve the minimum element in the stack.
     *
     */
    static class MinStack {
        // Main stack to store all elements.
        Deque<Integer> stack;

        // Auxiliary stack to keep track of the minimum elements.
        Deque<Integer> minStack;

        public MinStack() {
            stack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }

        public void push(int val) {
            // Push the element onto the main stack.
            stack.push(val);

            // Push the element to minStack only if it is the minimum so far.
            // This ensures that minStack always contains the minimum element at the top.
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            // Check if the top element of the main stack is the same as the top of the minStack.
            // If yes, it means we are removing the current minimum, so remove it from minStack too.
            if (stack.peek().equals(minStack.peek())) {
                minStack.pop();
            }

            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
