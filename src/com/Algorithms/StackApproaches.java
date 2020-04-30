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
        if (s.equals("(") || s.equals(")"))
            return false;
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
}
