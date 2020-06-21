package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backtracking {
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

    private void subsetsWithoutDup(List<List<Integer>> resultList, List<Integer> tmp, int[] nums, int pointer) {
        resultList.add(new ArrayList<>(tmp));
        for (int i = pointer; i < nums.length; i++) {
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
        List<List<Integer>> resultList = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(resultList, tmp, nums, 0);
        return resultList;
    }

    private void subsetsWithDup(List<List<Integer>> resultList, List<Integer> tmp, int[] nums, int pointer) {
        resultList.add(new ArrayList<>(tmp));
        for (int i = pointer; i < nums.length; i++) {
            if (i > pointer && nums[i] == nums[i - 1])
                continue;
            tmp.add(nums[i]);
            subsetsWithDup(resultList, tmp, nums, i + 1);
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
        partitionWithoutDup(resultList, tmp, sChar, 0);
        return resultList;
    }

    private void partitionWithoutDup(List<List<String>> resultList, List<String> tmp, char[] sChar, int pointer) {
        if (pointer == sChar.length)  //Add the list only when we consider all the characters in the given string
            resultList.add(new ArrayList<>(tmp));
        StringBuilder builder = new StringBuilder();
        for (int i = pointer; i < sChar.length; i++) {
            builder.append(sChar[i]);
            if (isPalindrome(builder.toString(), 0, builder.length() - 1)) {
                tmp.add(builder.toString());
                partitionWithoutDup(resultList, tmp, sChar, i + 1);  //At some point i + 1 will be the length of sChar.
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

    /**
     * Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.  Return a list of all possible strings we could create.
     *
     * Examples:
     * Input: S = "a1b2"
     * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
     *
     * Input: S = "3z4"
     * Output: ["3z4", "3Z4"]
     *
     * Input: S = "12345"
     * Output: ["12345"]
     * @param S
     * @return
     */
    List<String> permutations = new ArrayList<>();
    public List<String> letterCasePermutation(String S) {
        char[] sChar = S.toCharArray();
        permuteUtil(permutations, sChar, 0);
        return permutations;
    }
    private void permuteUtil(List<String> permutations, char[] sChar, int pointer) {
        if (pointer == sChar.length) {
            permutations.add(String.valueOf(sChar));
        }
        else {
            if (Character.isLetter(sChar[pointer])) {
                sChar[pointer] = Character.toLowerCase(sChar[pointer]);
                permuteUtil(permutations, sChar, pointer + 1);
                sChar[pointer] = Character.toUpperCase(sChar[pointer]);
            }
            permuteUtil(permutations, sChar, pointer + 1);
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
    List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatAllowed(int[] candidates, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumRepeatAllowed(candidates, target, tmp, 0);
        return resultList;
    }

    private void combinationSumRepeatAllowed(int[] candidates, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;
        else if (target == 0)
            resultList.add(new ArrayList<>(tmp));
        for (int i = pointer; i < candidates.length; i++) {
            tmp.add(candidates[i]);
            combinationSumRepeatAllowed(candidates, target - candidates[i], tmp, i);
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
//    List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatNotAllowed(int[] candidates, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumRepeatNotAllowed(candidates, target, tmp, 0);
        return resultList;
    }

    private void combinationSumRepeatNotAllowed(int[] candidates, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;
        else if (target == 0)
            resultList.add(new ArrayList<>(tmp));
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
    public List<List<Integer>> permuteDistinctNumbers(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        List<List<Integer>> resultList = new ArrayList<>();
        permuteUtil(nums, tmp, resultList);
        return resultList;
    }

    private void permuteUtil(int[] nums, List<Integer> tmp, List<List<Integer>> resultList) {
        if (tmp.size() == nums.length)
            resultList.add(new ArrayList<>(tmp));
        for (int num : nums) {
            if (tmp.contains(num))
                continue;
            tmp.add(num);
            permuteUtil(nums, tmp, resultList);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
     *
     * Example:
     *
     * Input: [1,1,2]
     * Output:
     * [
     *   [1,1,2],
     *   [1,2,1],
     *   [2,1,1]
     * ]
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteDuplicateNumbers(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        List<List<Integer>> resultList = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        permuteUtil(nums, tmp, resultList, visited);
        return resultList;
    }

    private void permuteUtil(int[] nums, List<Integer> tmp, List<List<Integer>> resultList, boolean[] visited) {
        if (tmp.size() == nums.length && !resultList.contains(tmp))
            resultList.add(new ArrayList<>(tmp));
        for (int i=0; i<nums.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                tmp.add(nums[i]);
                permuteUtil(nums, tmp, resultList, visited);
                tmp.remove(tmp.size() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * The set [1,2,3,...,n] contains a total of n! unique permutations.
     *
     * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * Given n and k, return the kth permutation sequence.
     *
     * Note:
     *
     * Given n will be between 1 and 9 inclusive.
     * Given k will be between 1 and n! inclusive.
     * Example 1:
     *
     * Input: n = 3, k = 3
     * Output: "213"
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        StringBuilder builder = new StringBuilder();
        boolean[] visited = new boolean[n + 1];
        return permuteUtil(n, builder, visited, k);
    }
    int count = 0;
    private String permuteUtil(int n, StringBuilder builder, boolean[] visited, int k) {
        if (builder.length() == n) {
            count += 1;
            if (count == k) {
                return builder.toString();
            }
        }
        for (int num = 1; num <= n; num++) {
            if (!visited[num]) {
                visited[num] = true;
                String ss = permuteUtil(n, builder.append(num), visited, k);
                if(ss != null)
                    return ss;
                builder.setLength(builder.length() - 1);
                visited[num] = false;
            }
        }
        return null;
    }
}
