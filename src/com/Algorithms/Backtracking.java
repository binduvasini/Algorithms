package com.Algorithms;

import java.util.*;

public class Backtracking {
    /**
     * Given a set of distinct integers, return all possible subsets (the power set).
     * The solution set must not contain duplicate subsets.
     * Input: nums = [1,2,3]
     * Output:
     * [
     * [],
     * [1],
     * [2],
     * [1, 2],
     * [1, 2, 3],
     * [1, 3],
     * [2],
     * [2, 3],
     * [3]
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsUniqueElements(int[] nums) {  // Runtime: O(n • 2^n)
        // We are copying the subset to a new list object. The runtime for that step is O(n)

        List<Integer> currentSubset = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        subsets(result, currentSubset, nums, 0);
        return result;
    }

    private void subsets(List<List<Integer>> result, List<Integer> currentSubset, int[] nums, int pointer) {
        // Start with an empty subset.
        // Add the current subset to the result.
        result.add(new ArrayList<>(currentSubset));

        // For each number, decide whether to include it in the current subset.
        for (int i = pointer; i < nums.length; i++) {

            // Include the current element
            currentSubset.add(nums[i]);

            // The pointer defines the current position in the array and
            // ensures that the subsequent numbers explored step by step.
            // So now, move to the next element.
            subsets(result, currentSubset, nums, i + 1);
            // The pointer increases to the size of the input array and then comes down to 0 in recursion.

            // Remove the last added element (backtrack) to try another subset.
            currentSubset.remove(currentSubset.size() - 1);
        }
    }

    /**
     * Given a collection of integers that contain duplicates, return all possible subsets (the power set).
     * Input: [1,2,2]
     * Output:
     * [
     * [],
     * [1],
     * [1, 2],
     * [1, 2, 2],
     * [2],
     * [2, 2]
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {  // Runtime: O(n • 2^n + n log n)
        List<Integer> currentSubset = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        // Sorting is required to group duplicate elements together.
        // This ensures we can skip duplicates easily during backtracking.
        Arrays.sort(nums);

        subsetsWithDup(result, currentSubset, nums, 0);
        return result;
    }

    private void subsetsWithDup(List<List<Integer>> result, List<Integer> currentSubset, int[] nums, int pointer) {
        // Add the current subset to the result
        result.add(new ArrayList<>(currentSubset));

        for (int i = pointer; i < nums.length; i++) {
            // Skip duplicates: only process the first occurrence of each number in this position
            if (i > pointer && nums[i] == nums[i - 1])
                continue;

            // Include the current element
            currentSubset.add(nums[i]);
            // Move to the next element
            subsetsWithDup(result, currentSubset, nums, i + 1);
            // Remove the last added element (backtrack)
            currentSubset.remove(currentSubset.size() - 1);
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
     *
     * @param s
     * @return
     */
    public List<List<String>> palindromePartition(String s) {  // Runtime: O(n•2^n)
        // Backtrack to explore all possible ways to split the string and check whether each substring is a palindrome.
        // At each step, we try all possible substrings starting from the current index and check if it's a palindrome.
        List<String> partition = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        partition(s, 0, partition, result);
        return result;
    }

    private void partition(String s, int start, List<String> partition, List<List<String>> result) {
        // If we've reached the end of the string, add the current partition to the result
        if (start == s.length()) {
            // Add the current partition to the result
            result.add(new ArrayList<>(partition));
            return;
        }

        // Try all possible substrings starting from the index 'start'
        for (int end = start; end < s.length(); end++) {
            // Check if the substring s[start...end] is a palindrome
            if (isPalindrome(s, start, end)) {
                // If it's a palindrome, add it to the current partition
                partition.add(s.substring(start, end + 1));

                // Recursively partition the remaining substring after this palindrome
                partition(s, end + 1, partition, result);

                // Remove the last added palindrome
                partition.remove(partition.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        // Check characters from both ends towards the center
        while (left < right) {
            // If characters don't match, it's not a palindrome
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            // Move the pointers inward
            left++;
            right--;
        }
        return true;
    }

    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     *
     * @param n
     * @return
     */
    public List<String> generateParentheses(int n) {  // Runtime: O(4^n)
        // Not a backtrack problem. More of a recursion.
        List<String> result = new ArrayList<>();

        // We maintain two counts:
        // open: The number of opening parentheses ( added so far.
        // close: The number of closing parentheses ) added so far.

        // Start recursion with an empty string, 0 open parentheses, and 0 close parentheses
        parentheses(n, "", 0, 0, result);
        return result;
    }

    private void parentheses(int n, String str, int open, int close, List<String> result) {
        // When the length of the current string reaches 2*n,
        // we have a complete valid combination of parentheses. Add it to the result list.
        if (str.length() == 2 * n) {
            result.add(str);
            return;
        }

        // If we can still add an opening parenthesis, do so
        if (open < n) {
            parentheses(n, str + "(", open + 1, close, result);
        }

        // Add a closing parenthesis if there are more opening parentheses than closing parentheses so far.
        if (close < open) {
            parentheses(n, str + ")", open, close + 1, result);
        }
    }

    /**
     * Given a string containing digits from 2-9 inclusive,
     * return all possible letter combinations that the number could represent.
     * digits = "23"
     * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        // Mapping of digits to corresponding letters using a HashMap
        Map<Character, String> digitToLetters = new HashMap<>();
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");

        // List to store the result
        List<String> result = new ArrayList<>();

        // Start the backtracking process
        backtrack(result, digits, digitToLetters, new StringBuilder(), 0);

        return result;
    }

    private void backtrack(
            List<String> result, String digits, Map<Character, String> digitToLetters, StringBuilder current, int index)
    {
        // Base case: if the current combination is complete
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // Get the current digit
        char digit = digits.charAt(index);

        // Get the letters corresponding to the current digit
        String letters = digitToLetters.get(digit);

        // Explore each letter
        for (char letter : letters.toCharArray()) {
            // Add the letter to the current combination
            current.append(letter);

            // Recursively process the next digit
            backtrack(result, digits, digitToLetters, current, index + 1);

            // Backtrack: remove the last letter to try the next option
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * Given an array nums and a target,
     * find all unique combinations in nums where the candidate numbers sums to target.
     * The same number may be chosen unlimited number of times.
     * The combination set will be unique as long as the frequency of the chosen numbers are different.
     * nums = [2,3,5], target = 8,
     * A solution set is:
     * [
     *  [2,2,2,2],
     *  [2,3,3],
     *  [3,5]
     * ]
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSumRepeatAllowed(int[] nums, int target) {
        // Runtime: O(2^n)
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        combinationSumRepeatAllowed(nums, target, combination, 0, result);
        return result;
    }

    private void combinationSumRepeatAllowed(
            int[] nums, int target, List<Integer> combination, int pointer, List<List<Integer>> result
    ) {
        if (target == 0) {  // Base case
            // If the target becomes zero, we've found a valid combination.
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = pointer; i < nums.length; i++) {
            // If the candidate exceeds the remaining target, skip it (not necessary for combinations)
            if (nums[i] > target) {
                continue;
            }
            combination.add(nums[i]);  // Choose the candidate

            // We consider the same element over and over.
            // That's why we pass the same index to the recursion.
            combinationSumRepeatAllowed(nums, target - nums[i], combination, i, result);

            combination.remove(combination.size() - 1);
        }
    }

    /**
     * Given an array nums and a target,
     * find all unique combinations in nums where the candidate numbers sums to target.
     * Each number in nums may only be used once in the combination.
     * nums = [10,1,2,7,6,1,5], target = 8,
     * A solution set is:
     * [
     *  [1, 7],
     *  [1, 2, 5],
     *  [2, 6],
     *  [1, 1, 6]
     * ]
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSumRepeatNotAllowed(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();

        // Sorting is required to group duplicate elements together.
        Arrays.sort(nums);
        combinationSumRepeatNotAllowed(nums, target, 0, combination, result);
        return result;
    }

    private void combinationSumRepeatNotAllowed(
            int[] nums, int target, int pointer, List<Integer> combination, List<List<Integer>> result
    ) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = pointer; i < nums.length; i++) {
            // Skip duplicates: if the current number is the same as the previous one, skip it
            if (i > pointer && nums[i] == nums[i - 1]) {
                continue;
            }

            // No need to continue if the current number is greater than the remaining target.
            // Exit the loop early. No further valid candidates exist once the current candidate exceeds the target.
            // The array is sorted already.
            if (nums[i] > target) {
                break;
            }

            combination.add(nums[i]);
            combinationSumRepeatNotAllowed(nums, target - nums[i], i + 1, combination, result);
            combination.remove(combination.size() - 1);
        }
    }

    /**
     * Given an integer array nums and an integer k,
     * return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.
     * nums = [4,3,2,3,5,2,1], k = 4
     * Output: true
     *
     * @param nums
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }

        if (total % k == 1)  //It's not possible to divide the array into k subsets if the total is not divisible by k.
            return false;

        Arrays.sort(nums);

        boolean[] visited = new boolean[nums.length];
        return subsetSum(nums, 0, total / k, 0, k, visited);
    }

    private boolean subsetSum(int[] nums, int ind, int target, int currSubsetSum, int k, boolean[] visited) {
        if (k == 0)  //k gets reduced to 0. We have formed k subsets with equal sums.
            return true;

        //The current subset sum gets increased to target.
        if (currSubsetSum == target) {  //Don't stop just yet. We still need to build more subsets up to k.
            return subsetSum(nums, 0, target, 0, k - 1, visited);
        }

        for (int i = ind; i < nums.length; i++) {
            if (visited[i])  //We are not supposed to reuse the elements.
                continue;

            visited[i] = true;
            //We are in the current element which we finished looking. The recursion must start from the next element.
            if (subsetSum(nums, i + 1, target, currSubsetSum + nums[i], k, visited))
                return true;

            visited[i] = false;  //Backtracking logic - reset the visited element.
        }

        return false;
    }

    /**
     * Given an array nums of distinct integers, return all possible permutations of a given array of distinct integers.
     * Input: [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {  // Runtime: O(n•n!)
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> permutation = new ArrayList<>();

        // We want to try every number in every position.
        // Instead of an index, track whether a number has been used by checking if it's already in the permutation list
        permute(nums, permutation, result);
        return result;
    }

    private void permute(int[] nums, List<Integer> permutation, List<List<Integer>> result) {
        // If the permutation size is equal to the array length, we have formed a valid permutation.
        if (permutation.size() == nums.length) {
            result.add(new ArrayList<>(permutation));
            return;
        }

        for (int num : nums) {
            // Skip numbers that are already in the permutation list to avoid duplicates
            if (permutation.contains(num)) {
                continue;
            }
            // Choose the current element.
            permutation.add(num);
            // Recur and continue building the permutation
            permute(nums, permutation, result);
            // Remove the last added element.
            permutation.remove(permutation.size() - 1);
        }
    }
}
