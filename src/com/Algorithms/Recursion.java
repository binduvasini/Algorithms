package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Given a set of distinct integers, nums, return all possible subsets (the power set). The solution set must not contain duplicate subsets.
     * Input: nums = [1,2,3]
     * Output:
     * [
     * [3],
     * [1],
     * [2],
     * [1,2,3],
     * [1,3],
     * [2,3],
     * [1,2],
     * []
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithoutDup(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        List<List<Integer>> resultList = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithoutDup(resultList, tmp, nums, 0);
        return resultList;
    }

    private void subsetsWithoutDup(List<List<Integer>> resultList, List<Integer> tmp, int[] nums, int point) {
        resultList.add(new ArrayList<>(tmp));
        for (int i = point; i < nums.length; i++) {
            tmp.add(nums[i]);
            subsetsWithoutDup(resultList, tmp, nums, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
     * Input: [1,2,2]
     * Output:
     * [
     * [2],
     * [1],
     * [1,2,2],
     * [2,2],
     * [1,2],
     * []
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        List<List<Integer>> l = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(l, tmp, nums, 0);
        return l;
    }

    private void subsetsWithDup(List<List<Integer>> l, List<Integer> tmp, int[] nums, int point) {
        l.add(new ArrayList<>(tmp));
        for (int i = point; i < nums.length; i++) {
            if (i > point && nums[i] == nums[i - 1])
                continue;
            tmp.add(nums[i]);
            subsetsWithDup(l, tmp, nums, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
     * The same repeated number may be chosen from candidates unlimited number of times.
     * Input: candidates = [2,3,5], target = 8,
     * A solution set is:
     * [
     * [2,2,2,2],
     * [2,3,3],
     * [3,5]
     * ]
     *
     * @param candidates
     * @param target
     * @return
     */
    List<List<Integer>> list = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatAllowed(int[] candidates, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumRepeatNotAllowed(candidates, target, tmp, 0);
        return lists;
    }

    private void combinationSumRepeatAllowed(int[] candidates, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;
        else if (target == 0)
            lists.add(new ArrayList<>(tmp));
        for (int i = pointer; i < candidates.length; i++) {
            tmp.add(candidates[i]);
            combinationSumRepeatNotAllowed(candidates, target - candidates[i], tmp, i);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
     * Each number in candidates may only be used once in the combination.
     * Input: candidates = [10,1,2,7,6,1,5], target = 8,
     * A solution set is:
     * [
     * [1, 7],
     * [1, 2, 5],
     * [2, 6],
     * [1, 1, 6]
     * ]
     *
     * @param candidates
     * @param target
     * @return
     */
    List<List<Integer>> lists = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatNotAllowed(int[] candidates, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumRepeatNotAllowed(candidates, target, tmp, 0);
        return lists;
    }

    private void combinationSumRepeatNotAllowed(int[] candidates, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;
        else if (target == 0)
            lists.add(new ArrayList<>(tmp));
        for (int i = pointer; i < candidates.length; i++) {
            if (i > pointer && candidates[i] == candidates[i - 1])
                continue;
            tmp.add(candidates[i]);
            combinationSumRepeatNotAllowed(candidates, target - candidates[i], tmp, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
     * Input: [1, 5, 11, 5]
     * Output: true
     * The array can be partitioned as [1, 5, 5] and [11].
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % 2 == 1)
            return false;
        Arrays.sort(nums);

        return subsetSum(nums, 0, total / 2);
    }

    private boolean subsetSum(int[] nums, int ind, int target) {
        boolean isValidSubset = false;
        if (target < 0)
            return false;
        else if (target == 0)
            return true;

        for (int i = ind; i < nums.length; i++) {
            if (i > ind && nums[i] == nums[i - 1])
                continue;
            isValidSubset = isValidSubset || subsetSum(nums, i + 1, target - nums[i]);
        }
        return isValidSubset;
    }

    /**
     * Given a collection of distinct integers, return all possible permutations.
     * Input: [1,2,3]
     * Output:
     * [
     * [1,2,3],
     * [1,3,2],
     * [2,1,3],
     * [2,3,1],
     * [3,1,2],
     * [3,2,1]
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        List<List<Integer>> l = new ArrayList<>();
        permuteUtil(nums, tmp, l);
        return l;
    }

    void permuteUtil(int[] nums, List<Integer> tmp, List<List<Integer>> l) {
        if (tmp.size() == nums.length)
            l.add(new ArrayList<>(tmp));
        for (int num : nums) {
            if (tmp.contains(num)) continue;
            tmp.add(num);
            permuteUtil(nums, tmp, l);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a string s, partition it such that every substring is a palindrome.
     * Return all possible palindrome partitioning of s.
     * Input: "aab"
     * Output:
     * [
     *   ["aa","b"],
     *   ["a","a","b"]
     * ]
     * @param s
     * @return
     */
    public List<List<String>> palindromePartition(String s) {
        List<String> tmp = new ArrayList<>();
        List<List<String>> resultList = new ArrayList<>();
        char[] sChar = s.toCharArray();  //Do not sort the array for this question.
        subsetsWithoutDup(resultList, tmp, sChar, 0);
        return resultList;
    }

    private void subsetsWithoutDup(List<List<String>> resultList, List<String> tmp, char[] sChar, int point) {
        if (point == sChar.length)  //Add the list only when we consider all the characters in the given string
            resultList.add(new ArrayList<>(tmp));
        StringBuilder builder = new StringBuilder();
        for (int i = point; i < sChar.length; i++) {
            builder.append(sChar[i]);
            if (isPalindrome(builder.toString(), 0, builder.length() - 1)) {
                tmp.add(builder.toString());

                subsetsWithoutDup(resultList, tmp, sChar, i + 1);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String str, int start, int end) {
        if (start >= end)
            return true;
        if (str.charAt(start) != str.charAt(end))
            return false;
        return isPalindrome(str, start + 1, end - 1);
    }
}
