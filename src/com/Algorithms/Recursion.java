package com.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recursion {
    List<String> combinations = new ArrayList<>();
    HashMap<Character, char[]> keypad = new HashMap<>();

    List<String> letterCombinations(String digits) {
        if (digits.length() == 0)
            return new ArrayList<>();

        keypad.put('2', new char[]{'a', 'b', 'c'});
        keypad.put('3', new char[]{'d', 'e', 'f'});
        keypad.put('4', new char[]{'g', 'h', 'i'});
        keypad.put('5', new char[]{'j', 'k', 'l'});
        keypad.put('6', new char[]{'m', 'n', 'o'});
        keypad.put('7', new char[]{'p', 'q', 'r', 's'});
        keypad.put('8', new char[]{'t', 'u', 'v'});
        keypad.put('9', new char[]{'w', 'x', 'y', 'z'});

        char[] digitsChar = digits.toCharArray();
        combine(digitsChar, 0, "");
        return combinations;
    }

    void combine(char[] digitsChar, int pointer, String combined) {
        if (pointer == digitsChar.length) {
            combinations.add(combined);
            return;
        }

        char[] charsFromKeypad = keypad.get(digitsChar[pointer]);
        for (char c : charsFromKeypad) {
            combine(digitsChar, pointer + 1, combined + "" + c);
        }
    }

    /*
      Backtracking problems
     */


}
