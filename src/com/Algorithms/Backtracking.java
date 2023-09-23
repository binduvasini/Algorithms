package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backtracking {
    /**
     * Given a set of distinct integers, nums, return all possible subsets (the power set).
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
            tmp.add(nums[i]); //include the current element
            subsetsWithoutDup(resultList, tmp, nums, i + 1);  //the pointer increases to the size of
            // the input array and then comes down to 0.
            tmp.remove(tmp.size() - 1); //when recursion comes back (pointer decrementing one by one),
            // we are backtracking. don't include the current element.
        }
    }

    /**
     * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
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
     *
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
                //At some point i + 1 will be the length of sChar.
                partitionWithoutDup(resultList, tmp, sChar, i + 1);
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
     * Given a string S,
     * we can transform every letter individually to be lowercase or uppercase to create another string.
     * Return a list of all possible strings we could create.

     * Examples:
     * Input: S = "a1b2"
     * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

     * Input: S = "3z4"
     * Output: ["3z4", "3Z4"]

     * Input: S = "12345"
     * Output: ["12345"]
     *
     * @param S
     * @return
     */
    List<String> permutations = new ArrayList<>();
    public List<String> letterCasePermutation(String S) {
        char[] sChar = S.toCharArray();
        permuteUtil(sChar, 0);
        return permutations;
    }

    private void permuteUtil(char[] sChar, int pointer) {
        if (pointer == sChar.length) {
            permutations.add(String.valueOf(sChar));
        }
        else {
            if (Character.isLetter(sChar[pointer])) {
                sChar[pointer] = Character.toLowerCase(sChar[pointer]);
                permuteUtil(sChar, pointer + 1);
                sChar[pointer] = Character.toUpperCase(sChar[pointer]);
            }
            permuteUtil(sChar, pointer + 1);
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
    List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatAllowed(int[] nums, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(nums);
        combinationSumRepeatAllowed(nums, target, tmp, 0);
        return resultList;
    }

    private void combinationSumRepeatAllowed(int[] nums, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;

        if (target == 0)
            resultList.add(new ArrayList<>(tmp));

        for (int i = pointer; i < nums.length; i++) {
            tmp.add(nums[i]);
            combinationSumRepeatAllowed(nums, target - nums[i], tmp, i);  //We consider the same element
            // over and over. That's why we pass the same index to the recursion.
            tmp.remove(tmp.size() - 1);
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
//    List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> combinationSumRepeatNotAllowed(int[] nums, int target) {
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(nums);
        combinationSumRepeatNotAllowed(nums, target, tmp, 0);
        return resultList;
    }

    private void combinationSumRepeatNotAllowed(int[] nums, int target, List<Integer> tmp, int pointer) {
        if (target < 0)
            return;

        if (target == 0)
            resultList.add(new ArrayList<>(tmp));

        for (int i = pointer; i < nums.length; i++) {
            if (i > pointer && nums[i] == nums[i - 1])
                continue;

            tmp.add(nums[i]);
            combinationSumRepeatNotAllowed(nums, target - nums[i], tmp, i + 1);
            tmp.remove(tmp.size() - 1);
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

        for (int i = 0; i < nums.length; i++) {
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
     * By listing and labeling all the permutations in order, we get the following sequence for n = 3:
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * Given n and k, return the kth permutation sequence.

     * Note:
     * Given n will be between 1 and 9 inclusive.
     * Given k will be between 1 and n! inclusive.

     * Input: n = 3, k = 3
     * Output: "213"
     *
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
                if (ss != null)
                    return ss;

                builder.setLength(builder.length() - 1);
                visited[num] = false;
            }
        }
        return null;
    }
}
