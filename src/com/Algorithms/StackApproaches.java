package com.Algorithms;

import java.util.Stack;
import java.util.*;

public class StackApproaches {

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
        // Use a stack to keep track of the expected closing parentheses
        Deque<Character> stack = new ArrayDeque<>();

        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If the character is an opening bracket, push the corresponding closing bracket onto the stack
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                // If the character is a closing bracket, check:
                // 1. Stack should not be empty (ensures matching opening bracket exists).
                // 2. The top of the stack must match the current closing bracket.
                // If either condition fails, the parentheses are invalid.
                return false;
        }

        // After processing all characters, the stack should be empty for the parentheses to be valid.
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
        // A Stack to keep track of indices of '(' (opening parentheses)
        Deque<Integer> openStack = new ArrayDeque<>();
        // A Stack to keep track of indices of '*' (wildcards)
        Deque<Integer> starStack = new ArrayDeque<>();

        // Iterate through the string character by character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                // Push the index of '(' onto the openStack
                openStack.push(i);
            else if (c == '*')
                // Push the index of '*' onto the starStack
                starStack.push(i);
            else {
                // If the current character is ')', try to match it:
                if (!openStack.isEmpty())
                    // Prefer matching with the nearest '('
                    openStack.pop();
                else if (!starStack.isEmpty())
                    // Otherwise, match with the nearest '*' (treated as '(')
                    starStack.pop();
                else
                    // If neither '(' nor '*' is available to match, the string is invalid
                    return false;
            }
        }

        // Handle any unmatched '(' after the main loop
        while (!openStack.isEmpty() && !starStack.isEmpty()) {
            // Ensure that each unmatched '(' has a corresponding '*' after it in the string
            // If the index of '(' is greater than the index of '*', it can't be matched
            if (openStack.pop() > starStack.pop())
                return false;
        }

        // If there are still unmatched '(', the string is invalid
        return openStack.isEmpty();
    }

    public int longestValidParentheses(String s) {
        // The stack stores indices of unmatched '(' characters. Each '(' index is pushed onto the stack.
        // For ), the top of the stack is popped, and
        // the length of the valid substring is calculated using the
        // difference between the current index and the index at the top of the stack.
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Initialize with -1 as a base index
        // Push -1 initially to handle cases where the valid substring starts at the beginning of the string.
        // For example, in the string "()", stack.peek will give -1, and the length will be: 1−(−1)=2

        int longest = 0;

        // Examine each character in the string.
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // Push index of '('. This keeps track of where potential valid substrings might start.
                stack.push(i);
            } else {
                stack.pop(); // Pop the top

                if (stack.isEmpty()) {
                    // If stack is empty, push current index as the new base
                    stack.push(i);
                } else {
                    // Calculate the length of the valid substring
                    longest = Math.max(longest, i - stack.peek());
                    // The stack is not empty. It already has the base reference for the next valid substring.
                }
            }
        }

        return longest;
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
        // Initialize a stack to store the index, where the index is the day index.
        Deque<Integer> stack = new ArrayDeque<>();

        int[] output = new int[temperatures.length];

        // Loop through the array of temperatures
        for (int i = 0; i < temperatures.length; i++) {
            int currTemp = temperatures[i];

            // When the current temperature is warmer than the immediate previous temperature
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

    /**
     * Decode an encoded string.
     * The encoding rule is: k[encoded_string],
     * where the encoded_string inside the square brackets is repeated exactly k times.
     * Input: "3[a]2[bc]"
     * Output: "aaabcbc"
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        // Stack to store the strings
        Stack<String> strStack = new Stack<>();
        // Stack to store the repeat counts
        Stack<Integer> numStack = new Stack<>();
        // StringBuilder to build the current substring
        StringBuilder currentStr = new StringBuilder();
        // Variable to build the current number
        int currentNum = 0;

        // Traverse each character in the input string
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // If the character is a digit, update the currentNum
                // This handles multi-digit numbers by shifting digits left (e.g., "23" -> 23)
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                // When encountering '[', push the currentNum and currentStr to their stacks
                // Save state before processing the nested/inner string
                numStack.push(currentNum); // Push repeat count
                strStack.push(currentStr.toString()); // Push the string built so far

                // Reset currentNum and currentStr for the new block
                currentNum = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                // When encountering ']', finish processing the current block
                // Pop the repeat count and the previous string from their stacks
                int repeatTimes = numStack.pop(); // Get the multiplier
                StringBuilder temp = new StringBuilder(strStack.pop()); // Get the string built before this block

                // Append the currentStr repeated 'repeatTimes' to the previous string
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(currentStr);
                }

                // Update currentStr to the decoded string for this block
                currentStr = temp;
            } else {
                // If the character is a letter, append it to the currentStr
                currentStr.append(c);
            }
        }

        // Return the fully decoded string
        return currentStr.toString();
    }
}
